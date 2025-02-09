package com.health.care.analyzer.dto.medicine;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicineRecordRequestDTO {
    @NotBlank(message = "name should not be blank")
    private String name;

    @NotNull(message = "quantity should not blank")
    private Integer quantity;
}
