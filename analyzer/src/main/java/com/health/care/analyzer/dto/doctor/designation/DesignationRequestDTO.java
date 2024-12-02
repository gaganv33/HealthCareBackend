package com.health.care.analyzer.dto.doctor.designation;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DesignationRequestDTO {
    @NotBlank(message = "qualification should not be blank")
    private String qualification;

    @NotBlank(message = "college name should not be blank")
    private String collegeName;
}
