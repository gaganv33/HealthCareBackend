package com.health.care.analyzer.controller.patient;

import com.health.care.analyzer.dto.profile.ProfileRequestDTO;
import com.health.care.analyzer.entity.userEntity.Patient;
import com.health.care.analyzer.entity.userEntity.User;
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

@RestController
@RequestMapping("/patient")
@PreAuthorize("hasAuthority('ROLE_PATIENT')")
public class PatientController {
    private final JwtService jwtService;
    private final UserService userService;
    private final PatientService patientService;

    @Autowired
    public PatientController(JwtService jwtService, UserService userService, PatientService patientService) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.patientService = patientService;
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
}
