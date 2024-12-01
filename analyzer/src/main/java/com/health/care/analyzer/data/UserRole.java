package com.health.care.analyzer.data;

import org.springframework.stereotype.Component;

@Component
public class UserRole {
    public static final String ADMIN = "ROLE_ADMIN";
    public static final String DOCTOR = "ROLE_DOCTOR";
    public static final String PATIENT = "ROLE_PATIENT";
    public static final String RECEPTIONIST = "ROLE_RECEPTIONIST";
    public static final String PHLEBOTOMIST = "ROLE_PHLEBOTOMIST";
    public static final String MEDICINE_VENDOR = "ROLE_MEDICINE_VENDOR";
}
