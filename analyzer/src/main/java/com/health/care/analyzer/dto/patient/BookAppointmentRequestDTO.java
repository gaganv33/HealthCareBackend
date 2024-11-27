package com.health.care.analyzer.dto.patient;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookAppointmentRequestDTO {
    @NotNull(message = "Time cannot be null")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime time;

    @NotNull(message = "Date cannot be null")
    @FutureOrPresent(message = "Date must be today or in the future")
    private LocalDate date;

    @NotBlank(message = "Doctor's username cannot be blank")
    private String doctorUsername;
}
