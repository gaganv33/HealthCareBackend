package com.health.care.analyzer.dto.labTestReport;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LabTestRequestDTO {
    @NotBlank(message = "test name should not be blank")
    private String name;

    @NotBlank(message = "details should not be blank")
    private String details;
}
