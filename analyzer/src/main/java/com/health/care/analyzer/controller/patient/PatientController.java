package com.health.care.analyzer.controller.patient;

import com.health.care.analyzer.dto.appointment.AppointmentResponseDTO;
import com.health.care.analyzer.dto.doctor.UserProfileResponseDTO;
import com.health.care.analyzer.dto.patient.BookAppointmentRequestDTO;
import com.health.care.analyzer.dto.profile.ProfileRequestDTO;
import com.health.care.analyzer.dto.profile.ProfileResponseDTO;
import com.health.care.analyzer.entity.Appointment;
import com.health.care.analyzer.entity.userEntity.Doctor;
import com.health.care.analyzer.entity.userEntity.Patient;
import com.health.care.analyzer.entity.userEntity.User;
import com.health.care.analyzer.service.appointment.AppointmentService;
import com.health.care.analyzer.service.auth.JwtService;
import com.health.care.analyzer.service.patient.PatientService;
import com.health.care.analyzer.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/patient")
@PreAuthorize("hasAuthority('ROLE_PATIENT')")
public class PatientController {
    private final JwtService jwtService;
    private final UserService userService;
    private final PatientService patientService;
    private final AppointmentService appointmentService;

    @Autowired
    public PatientController(JwtService jwtService, UserService userService, PatientService patientService,
                             AppointmentService appointmentService) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.patientService = patientService;
        this.appointmentService = appointmentService;
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
            @RequestParam(name = "date", required = false) Date date, HttpServletRequest httpServletRequest) {
        String authorization = httpServletRequest.getHeader("Authorization");
        String token = authorization.substring(7);
        String patientUsername = jwtService.extractUsername(token);
        Patient patient = userService.findByUsername(patientUsername).getPatient();

        List<AppointmentResponseDTO> appointments = appointmentService.getAllAppointmentUsingPatient(patient);

        if(stage != null && doctorUsername == null && date == null) {
            appointments = appointmentService.getAllAppointmentUsingPatientAndStage(patient, stage);
        }

        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }
}
