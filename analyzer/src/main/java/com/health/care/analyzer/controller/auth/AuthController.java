package com.health.care.analyzer.controller.auth;

import com.health.care.analyzer.dto.user.UserRequestDTO;
import com.health.care.analyzer.dto.user.UserResponseDTO;
import com.health.care.analyzer.entity.users.User;
import com.health.care.analyzer.exception.UsernameAlreadyTakenException;
import com.health.care.analyzer.service.auth.JwtService;
import com.health.care.analyzer.service.auth.refreshToken.RefreshTokenService;
import com.health.care.analyzer.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager,
                          RefreshTokenService refreshTokenService, JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
    }

    @GetMapping("/test")
    public String testRoute() {
        return "Test Route";
    }

    @PostMapping("/register")
    public UserResponseDTO registerUser(@RequestBody @Valid UserRequestDTO userRequestDTO) throws UsernameAlreadyTakenException {
        User user = new User(userRequestDTO);
        user = userService.save(user);
        return new UserResponseDTO(user);
    }
}
