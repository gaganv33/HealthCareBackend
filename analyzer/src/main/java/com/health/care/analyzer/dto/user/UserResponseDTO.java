package com.health.care.analyzer.dto.user;


import com.health.care.analyzer.entity.userEntity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    private Date registeredDate;

    public UserResponseDTO(User user) {
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.roles = user.getRole();
        this.isEnabled = user.getIsEnabled();
        this.registeredDate = user.getRegisteredDate();
    }
}
