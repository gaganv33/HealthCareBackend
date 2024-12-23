package com.health.care.analyzer.controller.phlebotomist;

import com.health.care.analyzer.dto.profile.ProfileRequestDTO;
import com.health.care.analyzer.entity.userEntity.Phlebotomist;
import com.health.care.analyzer.entity.userEntity.User;
import com.health.care.analyzer.service.auth.JwtService;
import com.health.care.analyzer.service.phlebotomist.PhlebotomistService;
import com.health.care.analyzer.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/phlebotomist")
@PreAuthorize("hasAuthority('ROLE_PHLEBOTOMIST')")
public class PhlebotomistController {
    private final UserService userService;
    private final PhlebotomistService phlebotomistService;

    @Autowired
    public PhlebotomistController(UserService userService, PhlebotomistService phlebotomistService) {
        this.userService = userService;
        this.phlebotomistService = phlebotomistService;
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
}
