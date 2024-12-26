package com.health.care.analyzer.controller.phlebotomist;

import com.health.care.analyzer.dto.labTestReport.PendingLabTestReportDTO;
import com.health.care.analyzer.dto.phlebotomistTest.ResultDTO;
import com.health.care.analyzer.dto.profile.ProfileRequestDTO;
import com.health.care.analyzer.dto.profile.ProfileResponseDTO;
import com.health.care.analyzer.entity.testEntity.LabTestReport;
import com.health.care.analyzer.entity.userEntity.Phlebotomist;
import com.health.care.analyzer.entity.userEntity.User;
import com.health.care.analyzer.exception.InvalidOperationException;
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
import java.util.Optional;

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

    @PostMapping("/lab/test/assign/{id}")
    public ResponseEntity<String> assignPendingLabTest(@PathVariable(name = "id") Long id,
                                                       HttpServletRequest httpServletRequest)
            throws InvalidOperationException {
        Phlebotomist phlebotomist = userService.getUserUsingAuthorizationHeader(httpServletRequest.getHeader("Authorization")).getPhlebotomist();
        Optional<LabTestReport> labTestReportOptional = labTestReportService.getPendingLabTestReportUsingId(id);
        if(labTestReportOptional.isEmpty()) {
            throw new InvalidOperationException("This lab test report does not exist or it is already assigned to another phlebotomist");
        }
        LabTestReport labTestReport = labTestReportOptional.get();
        labTestReport.setPhlebotomist(phlebotomist);
        labTestReportService.merge(labTestReport);
        return new ResponseEntity<>("Assigned the lab test report successfully", HttpStatus.OK);
    }

    @PostMapping("/lab/test/result/{id}")
    public ResponseEntity<String> saveResultForLabTestUsingId(@PathVariable(name = "id") Long id,
                                                              @RequestBody @Valid ResultDTO resultDTO,
                                                              HttpServletRequest httpServletRequest)
            throws InvalidOperationException {
        Phlebotomist phlebotomist = userService.getUserUsingAuthorizationHeader(httpServletRequest.getHeader("Authorization")).getPhlebotomist();
        Optional<LabTestReport> labTestReportOptional = labTestReportService.getPendingLabTestReportUsingIdAndPhlebotomist(id, phlebotomist);
        if(labTestReportOptional.isEmpty()) {
            throw new InvalidOperationException("This lab test report does not exist or the result is already updated or " +
                    "this lab test report is not assigned to this phlebotomist");
        }
        LabTestReport labTestReport = labTestReportOptional.get();
        labTestReport.setResult(resultDTO.getResult());
        labTestReportService.merge(labTestReport);
        return new ResponseEntity<>("Result of the lab test report saved successfully", HttpStatus.OK);
    }

    @GetMapping("/lab/test/pending/result")
    public ResponseEntity<List<PendingLabTestReportDTO>> getAllPendingLabTestUsingPhlebotomist(HttpServletRequest httpServletRequest) {
        Phlebotomist phlebotomist = userService.getUserUsingAuthorizationHeader(httpServletRequest.getHeader("Authorization")).getPhlebotomist();
        return new ResponseEntity<>(labTestReportService.getAllPendingLabTestUsingPhlebotomist(phlebotomist), HttpStatus.OK);
    }
}
