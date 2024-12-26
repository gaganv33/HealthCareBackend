package com.health.care.analyzer.controller.phlebotomist;

import com.health.care.analyzer.dto.labTestReport.PendingLabTestReportDTO;
import com.health.care.analyzer.dto.profile.ProfileRequestDTO;
import com.health.care.analyzer.dto.profile.ProfileResponseDTO;
import com.health.care.analyzer.entity.userEntity.Phlebotomist;
import com.health.care.analyzer.entity.userEntity.User;
import com.health.care.analyzer.service.labTestReport.LabTestReportService;
import com.health.care.analyzer.service.phlebotomist.PhlebotomistService;
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
@RequestMapping("/phlebotomist")
@PreAuthorize("hasAuthority('ROLE_PHLEBOTOMIST')")
public class PhlebotomistController {
    private final UserService userService;
    private final PhlebotomistService phlebotomistService;
    private final LabTestReportService labTestReportService;

    @Autowired
    public PhlebotomistController(UserService userService, PhlebotomistService phlebotomistService,
                                  LabTestReportService labTestReportService) {
        this.userService = userService;
        this.phlebotomistService = phlebotomistService;
        this.labTestReportService = labTestReportService;
    }

    @GetMapping("/test")
    public String testRoute() {
        return "Phlebotomist route";
    }

    @PostMapping("/profile")
    public ResponseEntity<String> phlebotomistProfile(@RequestBody @Valid ProfileRequestDTO profileRequestDTO,
                                                      HttpServletRequest httpServletRequest) {
        Phlebotomist phlebotomist = new Phlebotomist(profileRequestDTO);
        User user = userService.getUserUsingAuthorizationHeader(httpServletRequest.getHeader("Authorization"));

        phlebotomist.setUser(user);
        phlebotomistService.save(phlebotomist);

        return new ResponseEntity<>("Phlebotomist Profile updated", HttpStatus.CREATED);
    }

    @GetMapping("/profile")
    public ResponseEntity<ProfileResponseDTO> getPhlebotomistProfile(HttpServletRequest httpServletRequest) {
        User user = userService.getUserUsingAuthorizationHeader(httpServletRequest.getHeader("Authorization"));
        return new ResponseEntity<>(phlebotomistService.getPhlebotomistProfile(user), HttpStatus.OK);
    }

    @GetMapping("/pending/lab/test")
    public ResponseEntity<List<PendingLabTestReportDTO>> getAllPendingLabTestReport() {
        return new ResponseEntity<>(labTestReportService.getAllPendingLabTestReport(), HttpStatus.OK);
    }
}
