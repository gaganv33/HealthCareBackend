package com.health.care.analyzer.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicineVendorRegisterRequestDTO {
    @NotBlank(message = "username should not be blank")
    @Email(message = "Invalid username. Username has to be email ID")
    private String username;

    @NotBlank(message = "firstName should not be blank")
    private String firstName;

    @NotBlank(message = "lastName should not be blank")
    private String lastName;

    @NotBlank(message = "password should not be blank")
    @Size(min = 4, max = 20, message = "Min password length is 4 and max password length is 20")
    private String password;

    @NotBlank(message = "roles should not be blank")
    @Pattern(regexp = "^ROLE_[A-Za-z0-9_]*$", message = "roles does not match the pattern 'ROLE_'")
    private String role;

    @NotBlank(message = "address should not be blank")
    private String address;
}
