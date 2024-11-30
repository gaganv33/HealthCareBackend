package com.health.care.analyzer.dto.user;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    @NotBlank(message = "username should not be blank")
    @Email(message = "Invalid username. Username has to be email ID")
    private String username;

    @NotBlank(message = "firstName should not be blank")
    private String firstName;

    @NotBlank(message = "lastName should not be blank")
    private String lastName;

    @NotNull(message = "Date of birth should not be null")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dob;

    @NotBlank(message = "Phone number should not be blank")
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Phone number must be 10 digits"
    )
    private String phoneNo;

    @NotBlank(message = "Blood group should not be blank")
    @Pattern(
            regexp = "^(A|B|AB|O)[+-]$",
            message = "Blood group must be A+, A-, B+, B-, AB+, AB-, O+, or O-"
    )
    private String bloodGroup;

    @NotNull(message = "height cannot be null")
    private Integer height;

    @NotNull(message = "weight cannot be null")
    private Integer weight;

    @NotBlank(message = "password should not be blank")
    @Size(min = 4, max = 20, message = "Min password length is 4 and max password length is 20")
    private String password;

    @NotBlank(message = "roles should not be blank")
    @Pattern(regexp = "^ROLE_[A-Za-z0-9_]*$", message = "roles does not match the pattern 'ROLE_'")
    private String role;
}
