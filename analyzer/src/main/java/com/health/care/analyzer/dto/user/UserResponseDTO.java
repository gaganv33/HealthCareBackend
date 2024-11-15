package com.health.care.analyzer.dto.user;


import com.health.care.analyzer.entity.users.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String roles;
    private Boolean isEnabled;

    public UserResponseDTO(User user) {
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.roles = user.getRole();
        this.isEnabled = user.getIsEnabled();
    }
}
