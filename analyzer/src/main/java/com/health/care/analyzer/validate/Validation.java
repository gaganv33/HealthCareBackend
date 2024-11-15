package com.health.care.analyzer.validate;

import org.springframework.stereotype.Component;

@Component
public class Validation {
    public boolean isRoleValid(String role) {
        return role.equals("ROLE_ADMIN") || role.equals("ROLE_PATIENT") || role.equals("ROLE_DOCTOR") ||
                role.equals("ROLE_RECEPTIONIST") || role.equals("ROLE_PHLEBOTOMIST");
    }
}
