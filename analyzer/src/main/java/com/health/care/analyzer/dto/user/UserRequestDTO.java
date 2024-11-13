package com.health.care.analyzer.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

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

    @NotBlank(message = "password should not be blank")
    @Size(min = 4, max = 20, message = "Min password length is 4 and max password length is 20")
    private String password;

    @NotBlank(message = "roles should not be blank")
    @Pattern(regexp = "^(ROLE_[^,]+)(,ROLE_[^,]+)*$", message = "roles does not match the pattern 'ROLE_'")
    private String roles;

    @NonNull
    private Boolean isEnabled;
}