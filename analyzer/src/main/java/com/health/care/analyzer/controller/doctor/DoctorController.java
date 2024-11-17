package com.health.care.analyzer.controller.doctor;

import com.health.care.analyzer.dto.doctor.DoctorRequestDTO;
import com.health.care.analyzer.entity.users.Doctor;
import com.health.care.analyzer.entity.users.User;
import com.health.care.analyzer.service.auth.JwtService;
import com.health.care.analyzer.service.doctor.DoctorService;
import com.health.care.analyzer.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/doctor")
@PreAuthorize("hasAuthority('ROLE_DOCTOR')")
public class DoctorController {
    private final JwtService jwtService;
    private final UserService userService;
    private final DoctorService doctorService;

    @Autowired
    public DoctorController(JwtService jwtService, UserService userService, DoctorService doctorService) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.doctorService = doctorService;
    }

    @GetMapping("/test")
    public String testRoute() {
        return "Doctor route";
    }

    @PostMapping("/profile")
    public ResponseEntity<String> doctorProfile(@RequestBody @Valid DoctorRequestDTO doctorRequestDTO,
                                                HttpServletRequest httpServletRequest) {
        Doctor doctor = new Doctor(doctorRequestDTO);

        String authorization = httpServletRequest.getHeader("Authorization");
        String token = authorization.substring(7);
        String username = jwtService.extractUsername(token);
        User user = userService.findByUsername(username);

        doctor.setUser(user);
        doctorService.save(doctor);

        return new ResponseEntity<>("Doctor Profile updated", HttpStatus.CREATED);
    }
}
