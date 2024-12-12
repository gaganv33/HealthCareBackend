package com.health.care.analyzer.controller.doctor;

import com.health.care.analyzer.dao.appointment.StageUpdateRequestDTO;
import com.health.care.analyzer.dto.appointment.AppointmentResponseDTO;
import com.health.care.analyzer.dto.doctor.designation.DesignationRequestDTO;
import com.health.care.analyzer.dto.doctor.designation.DesignationResponseDTO;
import com.health.care.analyzer.dto.labTestReport.LabTestReportDTO;
import com.health.care.analyzer.dto.labTestReport.LabTestRequestDTO;
import com.health.care.analyzer.dto.medicine.MedicineRecordRequestDTO;
import com.health.care.analyzer.dto.phlebotomistTest.PhlebotomistTestResponseDTO;
import com.health.care.analyzer.dto.prescription.PrescriptionResponseDTO;
import com.health.care.analyzer.dto.profile.ProfileRequestDTO;
import com.health.care.analyzer.dto.profile.ProfileResponseDTO;
import com.health.care.analyzer.entity.Appointment;
import com.health.care.analyzer.entity.Designation;
import com.health.care.analyzer.entity.Prescription;
import com.health.care.analyzer.entity.medicineEntity.MedicineRecord;
import com.health.care.analyzer.entity.testEntity.LabTestReport;
import com.health.care.analyzer.entity.testEntity.PhlebotomistTest;
import com.health.care.analyzer.entity.userEntity.Doctor;
import com.health.care.analyzer.entity.userEntity.User;
import com.health.care.analyzer.exception.InvalidOperationException;
import com.health.care.analyzer.exception.PhlebotomistTestResultNotFoundException;
import com.health.care.analyzer.exception.PrescriptionNotFoundException;
import com.health.care.analyzer.exception.appointment.InvalidAppointmentIdException;
import com.health.care.analyzer.service.appointment.AppointmentService;
import com.health.care.analyzer.service.doctor.DoctorService;
import com.health.care.analyzer.service.user.UserService;
import com.health.care.analyzer.utils.NotificationHelper;
import com.health.care.analyzer.utils.ValidationHelper;
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
    private final ValidationHelper validationHelper;
    private final NotificationHelper notificationHelper;

    @Autowired
    public DoctorController(UserService userService, DoctorService doctorService, AppointmentService appointmentService,
                            ValidationHelper validationHelper, NotificationHelper notificationHelper) {
        this.userService = userService;
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
        this.validationHelper = validationHelper;
        this.notificationHelper = notificationHelper;
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
        if(!validationHelper.isValidStageUpdateInDoctor(stageUpdateRequestDTO)) {
            throw new InvalidOperationException("Invalid stage value");
        }
        Doctor doctor = userService.getUserUsingAuthorizationHeader(httpServletRequest.getHeader("Authorization")).getDoctor();
        Optional<Appointment> appointmentOptional = appointmentService.getAppointmentById(id);

        Appointment appointment = validationHelper.getAppointmentAfterChecks(appointmentOptional, doctor);

        appointment.setStage(stageUpdateRequestDTO.getStage());
        appointmentService.merge(appointment);
        return new ResponseEntity<>(notificationHelper.getStageUpdateMessage(stageUpdateRequestDTO), HttpStatus.OK);
    }

    @PutMapping("/appointment/prescription/{id}")
    public ResponseEntity<String> addMedicine(@PathVariable(name = "id") Long id,
                                              @RequestBody @Valid MedicineRecordRequestDTO medicineRecordRequestDTO,
                                              HttpServletRequest httpServletRequest)
            throws InvalidAppointmentIdException, InvalidOperationException {
        Doctor doctor = userService.getUserUsingAuthorizationHeader(httpServletRequest.getHeader("Authorization")).getDoctor();
        Optional<Appointment> appointmentOptional = appointmentService.getAppointmentById(id);

        Appointment appointment = validationHelper.getAppointmentAfterChecks(appointmentOptional, doctor);

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

        Appointment appointment = validationHelper.getAppointmentAfterChecks(appointmentOptional, doctor);

        if(appointment.getPrescription() == null) {
            throw new PrescriptionNotFoundException("Prescription not found for this appointment");
        }
        PrescriptionResponseDTO prescriptionResponseDTO = new PrescriptionResponseDTO(appointment.getPrescription());
        return new ResponseEntity<>(prescriptionResponseDTO, HttpStatus.OK);
    }

    @PutMapping("/appointment/phlebotomist/test/{id}")
    public ResponseEntity<String> addPhlebotomistTest(@PathVariable(name = "id") Long id,
                                                      @RequestBody @Valid LabTestRequestDTO LabTestRequestDTO,
                                                      HttpServletRequest httpServletRequest)
            throws InvalidAppointmentIdException, InvalidOperationException {
        Doctor doctor = userService.getUserUsingAuthorizationHeader(httpServletRequest.getHeader("Authorization")).getDoctor();
        Optional<Appointment> appointmentOptional = appointmentService.getAppointmentById(id);

        Appointment appointment = validationHelper.getAppointmentAfterChecks(appointmentOptional, doctor);
        LabTestReport labTestReport = new LabTestReport(LabTestRequestDTO);
        if(appointment.getPhlebotomistTest() == null) {
            PhlebotomistTest phlebotomistTest = PhlebotomistTest.builder().appointment(appointment).build();
            phlebotomistTest.addLabTestReport(labTestReport);
            appointment.setPhlebotomistTest(phlebotomistTest);
        } else {
            appointment.getPhlebotomistTest().addLabTestReport(labTestReport);
        }
        labTestReport.setPhlebotomistTest(appointment.getPhlebotomistTest());
        appointmentService.merge(appointment);
        return new ResponseEntity<>("Lab Test added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/appointment/phlebotomist/test/{id}")
    public ResponseEntity<PhlebotomistTestResponseDTO> getPhlebotomistTestResult(@PathVariable(name = "id") Long id,
                                                                                 HttpServletRequest httpServletRequest)
            throws InvalidAppointmentIdException, InvalidOperationException, PhlebotomistTestResultNotFoundException {
        Doctor doctor = userService.getUserUsingAuthorizationHeader(httpServletRequest.getHeader("Authorization")).getDoctor();
        Optional<Appointment> appointmentOptional = appointmentService.getAppointmentById(id);

        Appointment appointment = validationHelper.getAppointmentAfterChecks(appointmentOptional, doctor);
        if(appointment.getPhlebotomistTest() == null) {
            throw new PhlebotomistTestResultNotFoundException("Phlebotomist test results not found");
        }
        return new ResponseEntity<>(new PhlebotomistTestResponseDTO(appointment.getPhlebotomistTest()), HttpStatus.OK);
    }
}
