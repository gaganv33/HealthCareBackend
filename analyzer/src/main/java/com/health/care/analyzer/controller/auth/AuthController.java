package com.health.care.analyzer.controller.auth;

import com.health.care.analyzer.data.UserRole;
import com.health.care.analyzer.dto.auth.AuthRequestDTO;
import com.health.care.analyzer.dto.auth.JwtResponse;
import com.health.care.analyzer.dto.auth.MedicineVendorRegisterRequestDTO;
import com.health.care.analyzer.dto.auth.RefreshTokenRequestDTO;
import com.health.care.analyzer.dto.user.UserRequestDTO;
import com.health.care.analyzer.dto.user.UserResponseDTO;
import com.health.care.analyzer.entity.RefreshToken;
import com.health.care.analyzer.entity.medicineEntity.MedicineVendor;
import com.health.care.analyzer.entity.userEntity.*;
import com.health.care.analyzer.exception.InvalidRefreshTokenException;
import com.health.care.analyzer.exception.InvalidRoleException;
import com.health.care.analyzer.exception.UnauthorizedException;
import com.health.care.analyzer.exception.UsernameAlreadyTakenException;
import com.health.care.analyzer.service.auth.JwtService;
import com.health.care.analyzer.service.auth.refreshToken.RefreshTokenService;
import com.health.care.analyzer.service.user.UserService;
import com.health.care.analyzer.validate.Validation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final Validation validation;

    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager,
                          RefreshTokenService refreshTokenService, JwtService jwtService,
                          Validation validation) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
        this.validation = validation;
    }

    @GetMapping("/test")
    public String testRoute() {
        return "Test Route";
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid UserRequestDTO userRequestDTO)
            throws UsernameAlreadyTakenException, InvalidRoleException {
        if(!validation.isRoleValid(userRequestDTO.getRole())) {
            throw new InvalidRoleException("Invalid role");
        }
        User user = new User(userRequestDTO);
        user.setIsEnabled(user.getRole().equals(UserRole.ADMIN) || user.getRole().equals(UserRole.PATIENT));
        user.setRegisteredDate(LocalDate.now());
        switch (user.getRole()) {
            case UserRole.ADMIN -> {
                Admin admin = Admin.builder()
                        .dob(userRequestDTO.getDob())
                        .phoneNo(userRequestDTO.getPhoneNo())
                        .bloodGroup(userRequestDTO.getBloodGroup())
                        .height(userRequestDTO.getHeight())
                        .weight(userRequestDTO.getWeight())
                        .user(user).build();
                user.setAdmin(admin);
            }
            case UserRole.DOCTOR -> {
                Doctor doctor = Doctor.builder()
                        .dob(userRequestDTO.getDob())
                        .phoneNo(userRequestDTO.getPhoneNo())
                        .bloodGroup(userRequestDTO.getBloodGroup())
                        .height(userRequestDTO.getHeight())
                        .weight(userRequestDTO.getWeight())
                        .user(user).build();
                user.setDoctor(doctor);
            }
            case UserRole.PATIENT -> {
                Patient patient = Patient.builder()
                        .dob(userRequestDTO.getDob())
                        .phoneNo(userRequestDTO.getPhoneNo())
                        .bloodGroup(userRequestDTO.getBloodGroup())
                        .height(userRequestDTO.getHeight())
                        .weight(userRequestDTO.getWeight())
                        .user(user).build();
                user.setPatient(patient);
            }
            case UserRole.RECEPTIONIST -> {
                Receptionist receptionist = Receptionist.builder()
                        .dob(userRequestDTO.getDob())
                        .phoneNo(userRequestDTO.getPhoneNo())
                        .bloodGroup(userRequestDTO.getBloodGroup())
                        .height(userRequestDTO.getHeight())
                        .weight(userRequestDTO.getWeight())
                        .user(user).build();
                user.setReceptionist(receptionist);
            }
            case UserRole.PHLEBOTOMIST -> {
                Phlebotomist phlebotomist = Phlebotomist.builder()
                        .dob(userRequestDTO.getDob())
                        .phoneNo(userRequestDTO.getPhoneNo())
                        .bloodGroup(userRequestDTO.getBloodGroup())
                        .height(userRequestDTO.getHeight())
                        .weight(userRequestDTO.getWeight())
                        .user(user).build();
                user.setPhlebotomist(phlebotomist);
            }
        }
        user = userService.save(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    @PostMapping("/register/vendor")
    public ResponseEntity<String> registerVendor(
            @RequestBody @Valid MedicineVendorRegisterRequestDTO medicineVendorRegisterRequestDTO)
            throws InvalidRoleException, UsernameAlreadyTakenException {
        if(!validation.isRoleValid(medicineVendorRegisterRequestDTO.getRole())) {
            throw new InvalidRoleException("Invalid role");
        }
        User user = new User(medicineVendorRegisterRequestDTO);
        user.setIsEnabled(false);
        user.setRegisteredDate(LocalDate.now());

        MedicineVendor medicineVendor = MedicineVendor.builder()
                .user(user)
                .address(medicineVendorRegisterRequestDTO.getAddress())
                .build();
        user.setMedicineVendor(medicineVendor);

        user = userService.save(user);

        return new ResponseEntity<>("Vendor registered successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody AuthRequestDTO authRequestDTO) throws UnauthorizedException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword())
        );
        if(authentication.isAuthenticated()) {
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDTO.getUsername());
            return JwtResponse
                    .builder()
                    .accessToken(jwtService.generateToken(authRequestDTO.getUsername()))
                    .refreshToken(refreshToken.getToken()).build();
        } else {
            throw new UnauthorizedException("Username " + authRequestDTO.getUsername() + " not found");
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest httpServletRequest) {
        String authorization = httpServletRequest.getHeader("Authorization");
        String token = authorization.substring(7);
        String username = jwtService.extractUsername(token);
        refreshTokenService.deleteTokenByUser(username);
        return new ResponseEntity<>("Logout successful", HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public JwtResponse getAccessToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO)
            throws InvalidRefreshTokenException {
        Optional<RefreshToken> refreshToken = refreshTokenService.findByToken(refreshTokenRequestDTO.getToken());
        if(refreshToken.isEmpty()) {
            throw new InvalidRefreshTokenException("Invalid refresh token");
        }
        RefreshToken token = refreshTokenService.verifyExpiration(refreshToken.get());
        User user = token.getUser();

        return JwtResponse
                .builder()
                .accessToken(jwtService.generateToken(user.getUsername()))
                .refreshToken(token.getToken())
                .build();
    }
}
