package com.health.care.analyzer.dto.appointment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponseDTO {
    private Date date;
    private String stage;
    private LocalTime time;
    private String doctorFirstName;
    private String doctorLastName;
}
