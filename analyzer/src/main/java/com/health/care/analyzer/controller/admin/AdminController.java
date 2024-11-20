package com.health.care.analyzer.controller.admin;

import com.health.care.analyzer.dto.profile.ProfileRequestDTO;
import com.health.care.analyzer.entity.userEntity.Admin;
import com.health.care.analyzer.entity.userEntity.User;
import com.health.care.analyzer.service.admin.AdminService;
import com.health.care.analyzer.service.auth.JwtService;
import com.health.care.analyzer.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {
    private final JwtService jwtService;
    private final UserService userService;
    private final AdminService adminService;

    @Autowired
    public AdminController(JwtService jwtService, UserService userService, AdminService adminService) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.adminService = adminService;
    }

    @GetMapping("/test")
    public String testRoute() {
        return "Admin route";
    }

    @PostMapping("/profile")
    public ResponseEntity<String> adminProfile(@RequestBody @Valid ProfileRequestDTO profileRequestDTO,
                                               HttpServletRequest httpServletRequest) {
        Admin admin = new Admin(profileRequestDTO);

        String authorization = httpServletRequest.getHeader("Authorization");
        String token = authorization.substring(7);
        String username = jwtService.extractUsername(token);
        User user = userService.findByUsername(username);

        admin.setUser(user);
        adminService.save(admin);

        return new ResponseEntity<>("Admin Profile updated", HttpStatus.CREATED);
    }
}
