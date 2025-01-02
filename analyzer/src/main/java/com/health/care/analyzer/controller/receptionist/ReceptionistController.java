package com.health.care.analyzer.controller.receptionist;

import com.health.care.analyzer.dto.prescription.NotAssignedPrescription;
import com.health.care.analyzer.dto.profile.ProfileRequestDTO;
import com.health.care.analyzer.dto.profile.ProfileResponseDTO;
import com.health.care.analyzer.entity.userEntity.Receptionist;
import com.health.care.analyzer.entity.userEntity.User;
import com.health.care.analyzer.exception.InvalidOperationException;
import com.health.care.analyzer.service.prescription.PrescriptionService;
import com.health.care.analyzer.service.receptionist.ReceptionistService;
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
@RequestMapping("/receptionist")
@PreAuthorize("hasAuthority('ROLE_RECEPTIONIST')")
public class ReceptionistController {
    private final UserService userService;
    private final ReceptionistService receptionistService;
    private final PrescriptionService prescriptionService;

    @Autowired
    public ReceptionistController(UserService userService, ReceptionistService receptionistService,
                                  PrescriptionService prescriptionService) {
        this.userService = userService;
        this.receptionistService = receptionistService;
        this.prescriptionService = prescriptionService;
    }

    @GetMapping("/test")
    public String testRoute() {
        return "Receptionist route";
    }

    @PostMapping("/profile")
    public ResponseEntity<String> receptionistProfile(@RequestBody @Valid ProfileRequestDTO profileRequestDTO,
                                                      HttpServletRequest httpServletRequest) {
        Receptionist receptionist = new Receptionist(profileRequestDTO);
        User user = userService.getUserUsingAuthorizationHeader(httpServletRequest.getHeader("Authorization"));

        receptionist.setUser(user);
        receptionistService.save(receptionist);

        return new ResponseEntity<>("Receptionist Profile updated", HttpStatus.CREATED);
    }

    @GetMapping("/profile")
    public ResponseEntity<ProfileResponseDTO> getReceptionistProfile(HttpServletRequest httpServletRequest) {
        User user = userService.getUserUsingAuthorizationHeader(httpServletRequest.getHeader("Authorization"));
        return new ResponseEntity<>(receptionistService.getReceptionistProfile(user), HttpStatus.OK);
    }

    @GetMapping("/pending/prescription")
    public ResponseEntity<List<NotAssignedPrescription>> getPendingPrescription() {
        return new ResponseEntity<>(prescriptionService.getNotAssignedPrescription(), HttpStatus.OK);
    }

    @PostMapping("/assign/prescription/{id}")
    public ResponseEntity<String> assignPrescription(@PathVariable(name = "id") Long id,
                                                     HttpServletRequest httpServletRequest)
            throws InvalidOperationException {
        Receptionist receptionist = userService.getUserUsingAuthorizationHeader(httpServletRequest.getHeader("Authorization")).getReceptionist();
        boolean result = prescriptionService.updatePrescriptionUsingIdAndReceptionist(id, receptionist);
        if(!result) {
            throw new InvalidOperationException("Prescription id is invalid or this prescription is already assigned to another receptionist");
        }
        return new ResponseEntity<>("Prescription is assigned successfully", HttpStatus.OK);
    }
}
