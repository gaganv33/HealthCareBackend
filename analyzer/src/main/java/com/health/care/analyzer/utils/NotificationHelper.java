package com.health.care.analyzer.utils;

import com.health.care.analyzer.dao.appointment.StageUpdateRequestDTO;
import com.health.care.analyzer.exception.InvalidOperationException;
import org.springframework.stereotype.Component;

@Component
public class NotificationHelper {
    public String getStageUpdateMessage(StageUpdateRequestDTO stageUpdateRequestDTO) throws InvalidOperationException {
        return switch (stageUpdateRequestDTO.getStage()) {
            case StageHelper.RECEPTIONIST -> "Appointment transferred to receptionist";
            case StageHelper.PHLEBOTOMIST -> "Appointment transferred to phlebotomist";
            case StageHelper.COMPLETED -> "Appointment completed";
            default -> throw new InvalidOperationException("Invalid stage");
        };
    }
}
