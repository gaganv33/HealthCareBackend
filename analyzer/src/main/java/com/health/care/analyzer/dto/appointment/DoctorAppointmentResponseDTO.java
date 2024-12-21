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
public class DoctorAppointmentResponseDTO {
    private Long id;
    private LocalDate date;
    private String stage;
    private LocalTime time;
    private String patientFirstName;
    private String patientLastName;
    private String patientUsername;

    public DoctorAppointmentResponseDTO(Appointment appointment) {
        this.id = appointment.getId();
        this.date = appointment.getDate();
        this.stage = appointment.getStage();
        this.time = appointment.getTime();
        this.patientFirstName = appointment.getPatient().getUser().getFirstName();
        this.patientLastName = appointment.getPatient().getUser().getFirstName();
        this.patientUsername = appointment.getPatient().getUser().getUsername();
    }
}
