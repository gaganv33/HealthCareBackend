package com.health.care.analyzer.controller.patient;

import com.health.care.analyzer.dto.appointment.AppointmentFeedbackRequestDTO;
import com.health.care.analyzer.dto.appointment.AppointmentResponseDTO;
import com.health.care.analyzer.dto.doctor.UserProfileResponseDTO;
import com.health.care.analyzer.dto.patient.BookAppointmentRequestDTO;
import com.health.care.analyzer.dto.profile.ProfileRequestDTO;
import com.health.care.analyzer.dto.profile.ProfileResponseDTO;
import com.health.care.analyzer.entity.Appointment;
import com.health.care.analyzer.entity.Feedback;
import com.health.care.analyzer.entity.userEntity.Doctor;
import com.health.care.analyzer.entity.userEntity.Patient;
import com.health.care.analyzer.entity.userEntity.User;
import com.health.care.analyzer.exception.DoctorNotFoundException;
import com.health.care.analyzer.exception.InvalidAppointmentIdException;
import com.health.care.analyzer.service.appointment.AppointmentService;
import com.health.care.analyzer.service.auth.JwtService;
import com.health.care.analyzer.service.feedback.FeedbackService;
import com.health.care.analyzer.service.patient.PatientService;
import com.health.care.analyzer.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/patient")
@PreAuthorize("hasAuthority('ROLE_PATIENT')")
public class PatientController {
    private final JwtService jwtService;
    private final UserService userService;
    private final PatientService patientService;
    private final AppointmentService appointmentService;
    private final FeedbackService feedbackService;

    @Autowired
    public PatientController(JwtService jwtService, UserService userService, PatientService patientService,
                             AppointmentService appointmentService, FeedbackService feedbackService) {
        this.jwtService = jwtService;
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

        String authorization = httpServletRequest.getHeader("Authorization");
        String token = authorization.substring(7);
        String username = jwtService.extractUsername(token);
        User user = userService.findByUsername(username);

        patient.setUser(user);
        patientService.save(patient);

        return new ResponseEntity<>("Patient Profile updated", HttpStatus.CREATED);
    }

    @GetMapping("/profile")
    public ResponseEntity<ProfileResponseDTO> getPatientProfile(HttpServletRequest httpServletRequest) {
        String authorization = httpServletRequest.getHeader("Authorization");
        String token = authorization.substring(7);
        String username = jwtService.extractUsername(token);
        User user = userService.findByUsername(username);

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
                                                  HttpServletRequest httpServletRequest) {
        String authorization = httpServletRequest.getHeader("Authorization");
        String token = authorization.substring(7);
        String patientUsername = jwtService.extractUsername(token);

        Patient patient = userService.findByUsername(patientUsername).getPatient();
        Doctor doctor = userService.findByUsername(bookAppointmentRequestDTO.getDoctorUsername()).getDoctor();
        Appointment appointment = Appointment.builder()
                .stage("doctor")
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
    public ResponseEntity<String> deleteAppointmentById(@PathVariable("id") long id) {
        appointmentService.deleteById(id);
        return new ResponseEntity<> ("Appointment deleted", HttpStatus.OK);
    }

    @GetMapping("/all/appointment")
    public ResponseEntity<List<AppointmentResponseDTO>> getAllAppointment(
            @RequestParam(name = "stage", required = false) String stage,
            @RequestParam(name = "doctor", required = false) String doctorUsername,
            HttpServletRequest httpServletRequest)
            throws DoctorNotFoundException {
        String authorization = httpServletRequest.getHeader("Authorization");
        String token = authorization.substring(7);
        String patientUsername = jwtService.extractUsername(token);
        Patient patient = userService.findByUsername(patientUsername).getPatient();

        List<AppointmentResponseDTO> appointments = getAllAppointmentBasedOnStageDoctorUsernameAndDate(
                patient, stage, doctorUsername);

        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    private List<AppointmentResponseDTO> getAllAppointmentBasedOnStageDoctorUsernameAndDate(Patient patient,
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
                                                 @RequestBody AppointmentFeedbackRequestDTO feedbackRequestDTO)
            throws InvalidAppointmentIdException {
        Optional<Appointment> appointmentOptional = appointmentService.getById(id);
        if(appointmentOptional.isEmpty()) {
            throw new InvalidAppointmentIdException("Invalid appointment id");
        }
        Appointment appointment = appointmentOptional.get();
        Feedback feedback = Feedback.builder().feedback(feedbackRequestDTO.getFeedback()).build();

        if(appointment.getFeedback() == null) {
            feedback = feedbackService.save(feedback);
        } else {
            Feedback feedbackAppointment = appointment.getFeedback();
            feedbackAppointment.setFeedback(feedback.getFeedback());
            feedback = feedbackService.update(feedbackAppointment);
        }
        appointmentService.updateFeedback(id, feedback);
        return new ResponseEntity<>("Feedback updated", HttpStatus.OK);
    }

    @GetMapping("/appointment/feedback/{id}")
    public ResponseEntity<Feedback> getAppointmentFeedbackById(@PathVariable(name = "id") Long id)
            throws InvalidAppointmentIdException {
        Optional<Feedback> feedbackOptional = appointmentService.getAppointmentFeedbackById(id);
        if(feedbackOptional.isEmpty()) {
            throw new InvalidAppointmentIdException("Invalid appointment id or no feedback for this appointment");
        }
        return new ResponseEntity<>(feedbackOptional.get(), HttpStatus.OK);
    }
}
