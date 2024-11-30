package com.health.care.analyzer.dto.appointment;

import com.health.care.analyzer.entity.Appointment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponseDTO {
    private Long id;
    private LocalDate date;
    private String stage;
    private LocalTime time;
    private String doctorFirstName;
    private String doctorLastName;
    private String doctorUsername;

    public AppointmentResponseDTO(Appointment appointment) {
        this.id = appointment.getId();
        this.date = appointment.getDate();
        this.stage = appointment.getStage();
        this.time = appointment.getTime();
        this.doctorFirstName = appointment.getDoctor().getUser().getFirstName();
        this.doctorLastName = appointment.getDoctor().getUser().getLastName();
        this.doctorUsername = appointment.getDoctor().getUser().getUsername();
    }
}
