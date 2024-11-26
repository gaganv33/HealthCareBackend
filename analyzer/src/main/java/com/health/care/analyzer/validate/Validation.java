package com.health.care.analyzer.validate;

import com.health.care.analyzer.data.UserRole;
import org.springframework.stereotype.Component;

@Component
public class Validation {
    public boolean isRoleValid(String role) {
        return role.equals(UserRole.ADMIN) || role.equals(UserRole.PATIENT) || role.equals(UserRole.DOCTOR) ||
                role.equals(UserRole.RECEPTIONIST) || role.equals(UserRole.PHLEBOTOMIST);
    }
}
