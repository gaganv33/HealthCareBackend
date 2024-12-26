package com.health.care.analyzer.dto.phlebotomistTest;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultDTO {
    @NotBlank(message = "result should not be blank")
    private String result;
}
