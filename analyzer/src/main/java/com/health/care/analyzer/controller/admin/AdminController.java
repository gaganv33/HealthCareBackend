package com.health.care.analyzer.controller.admin;


import com.health.care.analyzer.data.UserRole;
import com.health.care.analyzer.dto.admin.ModifyUserRequestDTO;
import com.health.care.analyzer.dto.profile.ProfileRequestDTO;
import com.health.care.analyzer.dto.profile.ProfileResponseDTO;
import com.health.care.analyzer.dto.user.UserResponseDTO;
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

import java.util.List;

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

    @GetMapping("/profile")
    public ResponseEntity<ProfileResponseDTO> getAdminProfile(HttpServletRequest httpServletRequest) {
        String authorization = httpServletRequest.getHeader("Authorization");
        String token = authorization.substring(7);
        String username = jwtService.extractUsername(token);
        User user = userService.findByUsername(username);

        return new ResponseEntity<>(adminService.getAdminProfile(user), HttpStatus.OK);
    }

    @GetMapping("/all/user")
    public ResponseEntity<List<UserResponseDTO>> getAllUser(@RequestParam(name = "role", required = false) String role,
                                                            @RequestParam(name = "isEnabled", required = false) Boolean isEnabled) {
        List<UserResponseDTO> userList = getAllUsersBasedOnRoleAndIsEnabled(role, isEnabled);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    private List<UserResponseDTO> getAllUsersBasedOnRoleAndIsEnabled(String role, Boolean isEnabled) {
        if(role == null && isEnabled == null) {
            return userService.getAllUser();
        } else if(role != null && isEnabled == null) {
            return getAllUsersBasedOnRole(role);
        } else if(role == null) {
            return getAllUsersBasedOnIsEnabled(isEnabled);
        }
        if(isEnabled) {
            return getAllEnabledUsersByRole(role);
        }
        return getAllDisabledUsersByRole(role);
    }

    private List<UserResponseDTO> getAllUsersBasedOnRole(String role) {
        return switch(role) {
            case UserRole.ADMIN -> userService.getAllAdmin();
            case UserRole.DOCTOR -> userService.getAllDoctor();
            case UserRole.PATIENT -> userService.getAllPatient();
            case UserRole.PHLEBOTOMIST -> userService.getAllPhlebotomist();
            case UserRole.RECEPTIONIST -> userService.getAllReceptionist();
            default -> userService.getAllUser();
        };
    }

    private List<UserResponseDTO> getAllUsersBasedOnIsEnabled(Boolean isEnabled) {
        return isEnabled ? userService.getEnabledUser() : userService.getDisabledUser();
    }

    private List<UserResponseDTO> getAllEnabledUsersByRole(String role) {
        return switch(role) {
            case UserRole.ADMIN -> userService.getAllEnabledAdmin();
            case UserRole.DOCTOR -> userService.getAllEnabledDoctor();
            case UserRole.PATIENT -> userService.getAllEnabledPatient();
            case UserRole.PHLEBOTOMIST -> userService.getAllEnabledPhlebotomist();
            case UserRole.RECEPTIONIST -> userService.getAllEnabledReceptionist();
            default -> userService.getAllUser();
        };
    }

    private final List<UserResponseDTO> getAllDisabledUsersByRole(String role) {
        return switch(role) {
            case UserRole.ADMIN -> userService.getAllDisabledAdmin();
            case UserRole.DOCTOR -> userService.getAllDisabledDoctor();
            case UserRole.PATIENT -> userService.getAllDisabledPatient();
            case UserRole.PHLEBOTOMIST -> userService.getAllDisabledPhlebotomist();
            case UserRole.RECEPTIONIST -> userService.getAllDisabledReceptionist();
            default -> userService.getAllUser();
        };
    }

    @PostMapping("/enable/user")
    public ResponseEntity<String> enableUser(@RequestBody @Valid ModifyUserRequestDTO modifyUserRequestDTO) {
        userService.enableUser(modifyUserRequestDTO.getUsername());
        return new ResponseEntity<>("User is enabled", HttpStatus.OK);
    }

    @PostMapping("/disable/user")
    public ResponseEntity<String> disableUser(@RequestBody @Valid ModifyUserRequestDTO modifyUserRequestDTO) {
        userService.disableUser(modifyUserRequestDTO.getUsername());
        return new ResponseEntity<>("User is disabled", HttpStatus.OK);
    }
}
