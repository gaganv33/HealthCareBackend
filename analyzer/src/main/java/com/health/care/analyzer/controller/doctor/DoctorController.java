package com.health.care.analyzer.controller.doctor;

import com.health.care.analyzer.dao.appointment.StageUpdateRequestDTO;
import com.health.care.analyzer.dto.appointment.AppointmentResponseDTO;
import com.health.care.analyzer.dto.doctor.designation.DesignationRequestDTO;
import com.health.care.analyzer.dto.doctor.designation.DesignationResponseDTO;
import com.health.care.analyzer.dto.medicine.MedicineRecordRequestDTO;
import com.health.care.analyzer.dto.prescription.PrescriptionResponseDTO;
import com.health.care.analyzer.dto.profile.ProfileRequestDTO;
import com.health.care.analyzer.dto.profile.ProfileResponseDTO;
import com.health.care.analyzer.entity.Appointment;
import com.health.care.analyzer.entity.Designation;
import com.health.care.analyzer.entity.Prescription;
import com.health.care.analyzer.entity.medicineEntity.MedicineRecord;
import com.health.care.analyzer.entity.userEntity.Doctor;
import com.health.care.analyzer.entity.userEntity.User;
import com.health.care.analyzer.exception.InvalidOperationException;
import com.health.care.analyzer.exception.PrescriptionNotFoundException;
import com.health.care.analyzer.exception.appointment.InvalidAppointmentIdException;
import com.health.care.analyzer.service.appointment.AppointmentService;
import com.health.care.analyzer.service.doctor.DoctorService;
import com.health.care.analyzer.service.user.UserService;
import com.health.care.analyzer.validate.Validation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/doctor")
@PreAuthorize("hasAuthority('ROLE_DOCTOR')")
public class DoctorController {
    private final UserService userService;
    private final DoctorService doctorService;
    private final AppointmentService appointmentService;
    private final Validation validation;

    @Autowired
    public DoctorController(UserService userService, DoctorService doctorService, AppointmentService appointmentService,
                            Validation validation) {
        this.userService = userService;
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
        this.validation = validation;
    }

    @GetMapping("/test")
    public String testRoute() {
        return "Doctor route";
    }

    @PostMapping("/profile")
    public ResponseEntity<String> doctorProfile(@RequestBody @Valid ProfileRequestDTO profileRequestDTO,
                                                HttpServletRequest httpServletRequest) {
        Doctor doctor = new Doctor(profileRequestDTO);
        User user = userService.getUserUsingAuthorizationHeader(httpServletRequest.getHeader("Authorization"));

        doctor.setUser(user);
        doctorService.save(doctor);

        return new ResponseEntity<>("Doctor Profile updated", HttpStatus.CREATED);
    }

    @GetMapping("/profile")
    public ResponseEntity<ProfileResponseDTO> getDoctorProfile(HttpServletRequest httpServletRequest) {
        User user = userService.getUserUsingAuthorizationHeader(httpServletRequest.getHeader("Authorization"));
        return new ResponseEntity<>(doctorService.getDoctorProfile(user), HttpStatus.OK);
    }

    @PostMapping("/designation")
    public ResponseEntity<String> addDesignation(@RequestBody @Valid DesignationRequestDTO designationRequestDTO,
                                                 HttpServletRequest httpServletRequest) {
        Doctor doctor = userService.getUserUsingAuthorizationHeader(httpServletRequest.getHeader("Authorization")).getDoctor();
        if(!doctor.isContainsDesignation(designationRequestDTO.getQualification())) {
            Designation designation = new Designation(designationRequestDTO);
            doctor.getDesignationList().add(designation);
            doctorService.merge(doctor);
        }
        return new ResponseEntity<>("Designation added", HttpStatus.CREATED);
    }

    @GetMapping("/designation")
    public ResponseEntity<List<DesignationResponseDTO>> getDoctorDesignation(HttpServletRequest httpServletRequest) {
        Doctor doctor = userService.getUserUsingAuthorizationHeader(httpServletRequest.getHeader("Authorization")).getDoctor();
        List<Designation> designationList = doctor.getDesignationList();
        List<DesignationResponseDTO> designationResponseDTOList = designationList.stream().map(DesignationResponseDTO::new).toList();
        return new ResponseEntity<>(designationResponseDTOList, HttpStatus.OK);
    }

    @GetMapping("/all/appointment")
    public ResponseEntity<List<AppointmentResponseDTO>> getAllAppointmentUsingDoctorAndDoctorStage(HttpServletRequest httpServletRequest) {
        Doctor doctor = userService.getUserUsingAuthorizationHeader(httpServletRequest.getHeader("Authorization")).getDoctor();
        return new ResponseEntity<>(appointmentService.getAllAppointmentUsingDoctorAndStage(doctor), HttpStatus.OK);
    }

    @PatchMapping("/appointment/stage/{id}")
    public ResponseEntity<String> appointmentStageToReceptionist(@PathVariable(name = "id") Long id,
            @RequestBody @Valid StageUpdateRequestDTO stageUpdateRequestDTO,
            HttpServletRequest httpServletRequest)
            throws InvalidAppointmentIdException, InvalidOperationException {
        if(!validation.isValidStageUpdateInDoctor(stageUpdateRequestDTO)) {
            throw new InvalidOperationException("Invalid stage value");
        }
        Doctor doctor = userService.getUserUsingAuthorizationHeader(httpServletRequest.getHeader("Authorization")).getDoctor();
        Optional<Appointment> appointmentOptional = appointmentService.getAppointmentById(id);
        Appointment appointment = getAppointmentWithDoctorCheck(appointmentOptional, doctor);
        if(!appointment.getStage().equals("doctor")) {
            throw new InvalidOperationException("Cannot change stage if appointment is not in doctor");
        }
        appointment.setStage(stageUpdateRequestDTO.getStage());
        appointmentService.merge(appointment);
        return new ResponseEntity<>(getStageUpdateMessage(stageUpdateRequestDTO), HttpStatus.OK);
    }

    private String getStageUpdateMessage(StageUpdateRequestDTO stageUpdateRequestDTO) throws InvalidOperationException {
        return switch (stageUpdateRequestDTO.getStage()) {
            case "receptionist" -> "Appointment transferred to receptionist";
            case "phlebotomist" -> "Appointment transferred to phlebotomist";
            case "completed" -> "Appointment completed";
            default -> throw new InvalidOperationException("Invalid stage");
        };
    }

    @PutMapping("/appointment/prescription/{id}")
    public ResponseEntity<String> addMedicine(@PathVariable(name = "id") Long id,
                                              @RequestBody @Valid MedicineRecordRequestDTO medicineRecordRequestDTO,
                                              HttpServletRequest httpServletRequest)
            throws InvalidAppointmentIdException, InvalidOperationException {
        Doctor doctor = userService.getUserUsingAuthorizationHeader(httpServletRequest.getHeader("Authorization")).getDoctor();
        Optional<Appointment> appointmentOptional = appointmentService.getAppointmentById(id);
        Appointment appointment = getAppointmentWithDoctorCheck(appointmentOptional, doctor);
        MedicineRecord medicineRecord = new MedicineRecord(medicineRecordRequestDTO);
        if(appointment.getPrescription() != null) {
            appointment.getPrescription().addMedicineToRequiredMedicineList(medicineRecord);
        } else {
            Prescription prescription = Prescription.builder()
                    .appointment(appointment)
                    .build();
            prescription.addMedicineToRequiredMedicineList(medicineRecord);
            appointment.setPrescription(prescription);
        }
        appointmentService.merge(appointment);

        return new ResponseEntity<>("Medicine added to the prescription", HttpStatus.CREATED);
    }

    @GetMapping("/appointment/prescription/{id}")
    public ResponseEntity<PrescriptionResponseDTO> getPrescriptionUsingAppointmentIdAndDoctor(
            @PathVariable(name = "id") Long id, HttpServletRequest httpServletRequest)
            throws InvalidAppointmentIdException, PrescriptionNotFoundException, InvalidOperationException {
        Doctor doctor = userService.getUserUsingAuthorizationHeader(httpServletRequest.getHeader("Authorization")).getDoctor();
        Optional<Appointment> appointmentOptional = appointmentService.getAppointmentById(id);
        Appointment appointment = getAppointmentWithDoctorCheck(appointmentOptional, doctor);
        if(appointment.getPrescription() == null) {
            throw new PrescriptionNotFoundException("Prescription not found for this appointment");
        }
        PrescriptionResponseDTO prescriptionResponseDTO = new PrescriptionResponseDTO(appointment.getPrescription());
        return new ResponseEntity<>(prescriptionResponseDTO, HttpStatus.OK);
    }

    private final Appointment getAppointmentWithDoctorCheck(Optional<Appointment> appointmentOptional, Doctor doctor)
            throws InvalidAppointmentIdException, InvalidOperationException {
        if(appointmentOptional.isEmpty()) {
            throw new InvalidAppointmentIdException("Invalid appointment id");
        }
        Appointment appointment = appointmentOptional.get();
        if(!appointment.getDoctor().getUser().getUsername().equals(doctor.getUser().getUsername())) {
            throw new InvalidOperationException("Doctor cannot access this appointment");
        }
        return appointment;
    }
}
