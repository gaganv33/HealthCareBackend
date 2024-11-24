package com.health.care.analyzer.controller.admin;

import com.health.care.analyzer.dto.admin.AdminResponseDTO;
import com.health.care.analyzer.dto.admin.ModifyUserRequestDTO;
import com.health.care.analyzer.dto.profile.ProfileRequestDTO;
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

import java.util.ArrayList;
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
    public ResponseEntity<AdminResponseDTO> getAdminProfile(HttpServletRequest httpServletRequest) {
        String authorization = httpServletRequest.getHeader("Authorization");
        String token = authorization.substring(7);
        String username = jwtService.extractUsername(token);
        User user = userService.findByUsername(username);

        Admin admin = adminService.getAdminProfile(user);
        AdminResponseDTO adminResponseDTO = AdminResponseDTO.builder()
                .dob(admin.getDob())
                .phoneNo(admin.getPhoneNo())
                .bloodGroup(admin.getBloodGroup())
                .weight(admin.getWeight())
                .height(admin.getHeight())
                .build();
        return new ResponseEntity<>(adminResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/all/user")
    public ResponseEntity<List<UserResponseDTO>> getAllUser(@RequestParam(name = "role", required = false) String role,
                                                            @RequestParam(name = "isEnabled", required = false) Boolean isEnabled) {
        List<User> userList = userService.getAllUser();

        if(role != null && isEnabled == null) {
            userList = switch (role) {
                case "ROLE_ADMIN" -> userService.getAllAdmin();
                case "ROLE_DOCTOR" -> userService.getAllDoctor();
                case "ROLE_PATIENT" -> userService.getAllPatient();
                case "ROLE_RECEPTIONIST" -> userService.getAllReceptionist();
                case "ROLE_PHLEBOTOMIST" -> userService.getAllPhlebotomist();
                default -> userList;
            };
        } else if(role == null && isEnabled != null) {
            if(isEnabled) {
                userList = userService.getEnabledUser();
            } else {
                userList = userService.getDisabledUser();
            }
        } else if(role != null && isEnabled != null) {
            if(role.equals("ROLE_ADMIN") && isEnabled) {
                userList = userService.getAllEnabledAdmin();
            } else if(role.equals("ROLE_ADMIN") && !isEnabled) {
                userList = userService.getAllDisabledAdmin();
            } else if(role.equals("ROLE_DOCTOR") && isEnabled) {
                userList = userService.getAllEnabledDoctor();
            } else if(role.equals("ROLE_DOCTOR") && !isEnabled) {
                userList = userService.getAllDisabledDoctor();
            } else if(role.equals("ROLE_PATIENT") && isEnabled) {
                userList = userService.getAllEnabledPatient();
            } else if(role.equals("ROLE_PATIENT") && !isEnabled) {
                userList = userService.getAllDisabledPatient();
            } else if(role.equals("ROLE_RECEPTIONIST") && isEnabled) {
                userList = userService.getAllEnabledReceptionist();
            } else if(role.equals("ROLE_RECEPTIONIST") && !isEnabled) {
                userList = userService.getAllDisabledReceptionist();
            } else if(role.equals("ROLE_PHLEBOTOMIST") && isEnabled) {
                userList = userService.getAllEnabledPhlebotomist();
            } else if(role.equals("ROLE_PHLEBOTOMIST") && !isEnabled) {
                userList = userService.getAllDisabledPhlebotomist();
            } else {
                userList = new ArrayList<>();
            }
        }

        List<UserResponseDTO> userResponseDTOList = userList.stream().map(UserResponseDTO::new).toList();
        return new ResponseEntity<>(userResponseDTOList, HttpStatus.OK);
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

    @PostMapping("/delete/user")
    public ResponseEntity<String> deleteUser(@RequestBody @Valid ModifyUserRequestDTO modifyUserRequestDTO) {
        userService.deleteUser(modifyUserRequestDTO.getUsername());
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }
}
