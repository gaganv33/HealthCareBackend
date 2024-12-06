package com.health.care.analyzer.validate;

import com.health.care.analyzer.dao.appointment.StageUpdateRequestDTO;
import com.health.care.analyzer.data.UserRole;
import org.springframework.stereotype.Component;

@Component
public class Validation {
    public boolean isRoleValid(String role) {
        return role.equals(UserRole.ADMIN) || role.equals(UserRole.PATIENT) || role.equals(UserRole.DOCTOR) ||
                role.equals(UserRole.RECEPTIONIST) || role.equals(UserRole.PHLEBOTOMIST)
                || role.equals(UserRole.MEDICINE_VENDOR);
    }
    public boolean isValidStageUpdateInDoctor(StageUpdateRequestDTO stageUpdateRequestDTO) {
        String stage = stageUpdateRequestDTO.getStage();
        return stage.equals("receptionist") || stage.equals("phlebotomist") || stage.equals("completed");
    }
}
