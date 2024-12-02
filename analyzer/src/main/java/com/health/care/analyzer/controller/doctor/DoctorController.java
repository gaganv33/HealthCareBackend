package com.health.care.analyzer.controller.doctor;

import com.health.care.analyzer.dto.appointment.AppointmentResponseDTO;
import com.health.care.analyzer.dto.doctor.designation.DesignationRequestDTO;
import com.health.care.analyzer.dto.doctor.designation.DesignationResponseDTO;
import com.health.care.analyzer.dto.profile.ProfileRequestDTO;
import com.health.care.analyzer.dto.profile.ProfileResponseDTO;
import com.health.care.analyzer.entity.Designation;
import com.health.care.analyzer.entity.userEntity.Doctor;
import com.health.care.analyzer.entity.userEntity.User;
import com.health.care.analyzer.service.appointment.AppointmentService;
import com.health.care.analyzer.service.doctor.DoctorService;
import com.health.care.analyzer.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
@PreAuthorize("hasAuthority('ROLE_DOCTOR')")
public class DoctorController {
    private final UserService userService;
    private final DoctorService doctorService;
    private final AppointmentService appointmentService;

    @Autowired
    public DoctorController(UserService userService, DoctorService doctorService, AppointmentService appointmentService) {
        this.userService = userService;
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
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

    @GetMapping("/appointment")
    public ResponseEntity<List<AppointmentResponseDTO>> getAllAppointment(HttpServletRequest httpServletRequest) {
        Doctor doctor = userService.getUserUsingAuthorizationHeader(httpServletRequest.getHeader("Authorization")).getDoctor();
        return new ResponseEntity<>(appointmentService.getAllAppointmentUsingDoctor(doctor), HttpStatus.OK);
    }
}
