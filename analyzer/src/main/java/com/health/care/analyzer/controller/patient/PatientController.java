package com.health.care.analyzer.controller.patient;

import com.health.care.analyzer.dto.appointment.AppointmentFeedbackRequestDTO;
import com.health.care.analyzer.dto.appointment.PatientAppointmentResponseDTO;
import com.health.care.analyzer.dto.doctor.UserProfileResponseDTO;
import com.health.care.analyzer.dto.feedback.FeedbackResponseDTO;
import com.health.care.analyzer.dto.patient.BookAppointmentRequestDTO;
import com.health.care.analyzer.dto.phlebotomistTest.PhlebotomistTestResponseDTO;
import com.health.care.analyzer.dto.prescription.PrescriptionResponseDTO;
import com.health.care.analyzer.dto.profile.ProfileRequestDTO;
import com.health.care.analyzer.dto.profile.ProfileResponseDTO;
import com.health.care.analyzer.entity.Appointment;
import com.health.care.analyzer.entity.Feedback;
import com.health.care.analyzer.entity.Prescription;
import com.health.care.analyzer.entity.testEntity.PhlebotomistTest;
import com.health.care.analyzer.entity.userEntity.Doctor;
import com.health.care.analyzer.entity.userEntity.Patient;
import com.health.care.analyzer.entity.userEntity.User;
import com.health.care.analyzer.exception.*;
import com.health.care.analyzer.exception.appointment.FeedbackNotFoundException;
import com.health.care.analyzer.exception.appointment.InvalidAppointmentIdException;
import com.health.care.analyzer.service.appointment.AppointmentService;
import com.health.care.analyzer.service.feedback.FeedbackService;
import com.health.care.analyzer.service.patient.PatientService;
import com.health.care.analyzer.service.user.UserService;
import com.health.care.analyzer.data.Stage;
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
@RequestMapping("/patient")
@PreAuthorize("hasAuthority('ROLE_PATIENT')")
public class PatientController {
    private final UserService userService;
    private final PatientService patientService;
    private final AppointmentService appointmentService;
    private final FeedbackService feedbackService;

    @Autowired
    public PatientController(UserService userService, PatientService patientService,
                             AppointmentService appointmentService, FeedbackService feedbackService) {
        this.userService = userService;
        this.patientService = patientService;
        this.appointmentService = appointmentService;
        this.feedbackService = feedbackService;
    }

    @GetMapping("/test")
    public String testRoute() {
        return "Patient route";
    }

    @PostMapping("/profile")
    public ResponseEntity<String> patientProfile(@RequestBody @Valid ProfileRequestDTO profileRequestDTO,
                                                 HttpServletRequest httpServletRequest) {
        Patient patient = new Patient(profileRequestDTO);
        User user = userService.getUserUsingAuthorizationHeader(httpServletRequest.getHeader("Authorization"));

        patient.setUser(user);
        patientService.save(patient);

        return new ResponseEntity<>("Patient Profile updated", HttpStatus.CREATED);
    }

    @GetMapping("/profile")
    public ResponseEntity<ProfileResponseDTO> getPatientProfile(HttpServletRequest httpServletRequest) {
        User user = userService.getUserUsingAuthorizationHeader(httpServletRequest.getHeader("Authorization"));
        return new ResponseEntity<>(patientService.getPatientProfile(user), HttpStatus.OK);
    }

    @GetMapping("/all/doctor/username")
    public ResponseEntity<List<String>> getEnabledDoctorUsername() {
        return new ResponseEntity<>(userService.getEnabledDoctorUsername(), HttpStatus.OK);
    }

    @GetMapping("/all/doctor/profile")
    public ResponseEntity<List<UserProfileResponseDTO>> getEnabledDoctorWithProfile() {
        return new ResponseEntity<>(userService.getEnabledDoctorProfile(), HttpStatus.OK);
    }

    @PostMapping("/book/appointment")
    public ResponseEntity<String> bookAppointment(@RequestBody @Valid BookAppointmentRequestDTO bookAppointmentRequestDTO,
                                                  HttpServletRequest httpServletRequest) throws DoctorNotFoundException {
        Patient patient = userService.getUserUsingAuthorizationHeader(httpServletRequest.getHeader("Authorization"))
                .getPatient();
        User userDoctor = userService.findByUsername(bookAppointmentRequestDTO.getDoctorUsername());
        Doctor doctor = userDoctor.getDoctor();

        if(doctor == null || !userDoctor.getIsEnabled()) {
            throw new DoctorNotFoundException("Doctor not found or doctor is not enabled");
        }

        Appointment appointment = Appointment.builder()
                .stage(Stage.DOCTOR)
                .time(bookAppointmentRequestDTO.getTime())
                .date(bookAppointmentRequestDTO.getDate())
                .doctor(doctor)
                .patient(patient)
                .build();
        doctor.addAppointment(appointment);
        patient.addAppointment(appointment);

        appointmentService.save(appointment);
        return new ResponseEntity<>("Appointment booked", HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/appointment/{id}")
    public ResponseEntity<String> deleteAppointmentById(@PathVariable("id") long id,
                                                        HttpServletRequest httpServletRequest)
            throws InvalidAppointmentIdException {
        Patient patient = userService.getUserUsingAuthorizationHeader(httpServletRequest.getHeader("Authorization"))
                .getPatient();
        appointmentService.deleteAppointmentByPatientAndId(patient, id);
        return new ResponseEntity<> ("Appointment deleted", HttpStatus.OK);
    }

    @GetMapping("/all/appointment")
    public ResponseEntity<List<PatientAppointmentResponseDTO>> getAllAppointment(
            @RequestParam(name = "stage", required = false) String stage,
            @RequestParam(name = "doctor", required = false) String doctorUsername,
            HttpServletRequest httpServletRequest)
            throws DoctorNotFoundException {
        Patient patient = userService.getUserUsingAuthorizationHeader(httpServletRequest.getHeader("Authorization"))
                .getPatient();

        List<PatientAppointmentResponseDTO> appointments = getAllAppointmentBasedOnStageDoctorUsername(
                patient, stage, doctorUsername);

        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    private List<PatientAppointmentResponseDTO> getAllAppointmentBasedOnStageDoctorUsername(Patient patient,
                                                                                            String stage,
                                                                                            String doctorUsername)
            throws DoctorNotFoundException {
        if (stage != null && doctorUsername == null) {
            return appointmentService.getAllAppointmentUsingPatientAndStage(patient, stage);
        } else if(stage == null && doctorUsername != null) {
            Doctor doctor = userService.findByUsername(doctorUsername).getDoctor();
            if(doctor == null) {
                throw new DoctorNotFoundException("Doctor with username " + doctorUsername + " not found");
            }
            return appointmentService.getAllAppointmentUsingPatientAndDoctor(patient, doctor);
        } else if(stage != null) {
            Doctor doctor = userService.findByUsername(doctorUsername).getDoctor();
            if(doctor == null) {
                throw new DoctorNotFoundException("Doctor with username " + doctorUsername + " not found");
            }
            return appointmentService.getAllAppointmentUsingPatientDoctorAndStage(patient, doctor, stage);
        }
        return appointmentService.getAllAppointmentUsingPatient(patient);
    }

    @PostMapping("/update/appointment/feedback/{id}")
    public ResponseEntity<String> updateFeedback(@PathVariable(name = "id") Long id,
                                                 @RequestBody AppointmentFeedbackRequestDTO feedbackRequestDTO,
                                                 HttpServletRequest httpServletRequest)
            throws InvalidAppointmentIdException {
        Patient patient = userService.getUserUsingAuthorizationHeader(httpServletRequest.getHeader("Authorization"))
                .getPatient();

        Optional<Appointment> appointmentOptional = appointmentService.getAppointmentUsingPatientAndId(patient, id);
        if(appointmentOptional.isEmpty()) {
            throw new InvalidAppointmentIdException("Invalid appointment id");
        }
        Appointment appointment = appointmentOptional.get();
        Feedback feedback = Feedback.builder().feedback(feedbackRequestDTO.getFeedback()).build();

        if(appointment.getFeedback() == null) {
            feedback = feedbackService.save(feedback);
        } else {
            feedback.setId(appointment.getFeedback().getId());
            feedback = feedbackService.update(feedback);
        }
        appointmentService.updateFeedback(id, feedback);
        return new ResponseEntity<>("Feedback updated", HttpStatus.OK);
    }

    @GetMapping("/appointment/feedback/{id}")
    public ResponseEntity<FeedbackResponseDTO> getAppointmentFeedbackById(@PathVariable(name = "id") Long id,
                                                                          HttpServletRequest httpServletRequest)
            throws InvalidAppointmentIdException, FeedbackNotFoundException {
        Patient patient = userService.getUserUsingAuthorizationHeader(httpServletRequest.getHeader("Authorization"))
                .getPatient();

        Optional<Appointment> appointmentOptional = appointmentService.getAppointmentUsingPatientAndId(patient, id);
        if(appointmentOptional.isEmpty()) {
            throw new InvalidAppointmentIdException("Invalid appointment id");
        }
        Feedback feedback = appointmentOptional.get().getFeedback();
        if(feedback == null) {
            throw new FeedbackNotFoundException("Feedback not found");
        }
        return new ResponseEntity<>(new FeedbackResponseDTO(feedback), HttpStatus.OK);
    }

    @GetMapping("/appointment/prescription/{id}")
    public ResponseEntity<PrescriptionResponseDTO> getAppointmentPrescriptionById(@PathVariable(name = "id") Long id,
                                                                                  HttpServletRequest httpServletRequest)
            throws InvalidAppointmentIdException, PrescriptionNotFoundException {
        Patient patient = userService.getUserUsingAuthorizationHeader(httpServletRequest.getHeader("Authorization"))
                .getPatient();

        Optional<Appointment> appointmentOptional = appointmentService.getAppointmentUsingPatientAndId(patient, id);
        if(appointmentOptional.isEmpty()) {
            throw new InvalidAppointmentIdException("Invalid appointment id");
        }
        Prescription prescription = appointmentOptional.get().getPrescription();
        if(prescription == null) {
            throw new PrescriptionNotFoundException("Prescription not found");
        }
        return new ResponseEntity<>(new PrescriptionResponseDTO(prescription), HttpStatus.OK);
    }

    @GetMapping("/appointment/phlebotomist/test/{id}")
    public ResponseEntity<PhlebotomistTestResponseDTO> getAppointmentPhlebotomistTestById(
            @PathVariable(name = "id") Long id, HttpServletRequest httpServletRequest)
            throws InvalidAppointmentIdException, PhlebotomistNotFoundException {
        Patient patient = userService.getUserUsingAuthorizationHeader(httpServletRequest.getHeader("Authorization"))
                .getPatient();

        Optional<Appointment> appointmentOptional = appointmentService.getAppointmentUsingPatientAndId(patient, id);
        if(appointmentOptional.isEmpty()) {
            throw new InvalidAppointmentIdException("Invalid appointment id");
        }
        PhlebotomistTest phlebotomistTest = appointmentOptional.get().getPhlebotomistTest();
        if(phlebotomistTest == null) {
            throw new PhlebotomistNotFoundException("Phlebotomist test details not found");
        }
        return new ResponseEntity<>(new PhlebotomistTestResponseDTO(phlebotomistTest), HttpStatus.OK);
    }
}
