package com.health.care.analyzer.utils;

import com.health.care.analyzer.data.Stage;
import com.health.care.analyzer.dto.appointment.StageUpdateRequestDTO;
import com.health.care.analyzer.exception.InvalidOperationException;
import org.springframework.stereotype.Component;

@Component
public class NotificationHelper {
    public String getStageUpdateMessage(StageUpdateRequestDTO stageUpdateRequestDTO) throws InvalidOperationException {
        return switch (stageUpdateRequestDTO.getStage()) {
            case Stage.RECEPTIONIST -> "Appointment transferred to receptionist";
            case Stage.PHLEBOTOMIST -> "Appointment transferred to phlebotomist";
            case Stage.COMPLETED -> "Appointment completed";
            default -> throw new InvalidOperationException("Invalid stage");
        };
    }
}
