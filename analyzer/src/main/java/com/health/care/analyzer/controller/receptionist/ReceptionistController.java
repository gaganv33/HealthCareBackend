package com.health.care.analyzer.controller.receptionist;

import com.health.care.analyzer.dto.profile.ProfileRequestDTO;
import com.health.care.analyzer.entity.userEntity.Receptionist;
import com.health.care.analyzer.entity.userEntity.User;
import com.health.care.analyzer.service.auth.JwtService;
import com.health.care.analyzer.service.receptionist.ReceptionistService;
import com.health.care.analyzer.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receptionist")
@PreAuthorize("hasAuthority('ROLE_RECEPTIONIST')")
public class ReceptionistController {
    private final JwtService jwtService;
    private final UserService userService;
    private final ReceptionistService receptionistService;

    @Autowired
    public ReceptionistController(JwtService jwtService, UserService userService, ReceptionistService receptionistService) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.receptionistService = receptionistService;
    }

    @GetMapping("/test")
    public String testRoute() {
        return "Receptionist route";
    }

    @PostMapping("/profile")
    public ResponseEntity<String> receptionistProfile(@RequestBody @Valid ProfileRequestDTO profileRequestDTO,
                                                      HttpServletRequest httpServletRequest) {
        Receptionist receptionist = new Receptionist(profileRequestDTO);

        String authorization = httpServletRequest.getHeader("Authorization");
        String token = authorization.substring(7);
        String username = jwtService.extractUsername(token);
        User user = userService.findByUsername(username);

        receptionist.setUser(user);
        receptionistService.save(receptionist);

        return new ResponseEntity<>("Receptionist Profile updated", HttpStatus.CREATED);
    }
}
