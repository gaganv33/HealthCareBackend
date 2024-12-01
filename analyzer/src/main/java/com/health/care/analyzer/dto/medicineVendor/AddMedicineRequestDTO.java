package com.health.care.analyzer.dto.medicineVendor;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddMedicineRequestDTO {
    @NotBlank(message = "name should not be blank")
    private String name;

    @NotNull(message = "quantity should not be blank")
    private Integer quantity;

    @NotBlank(message = "serial no should not be blank")
    private String serialNo;

    @NotNull(message = "expiry date should blank")
    @FutureOrPresent(message = "expiry date should always be future or present")
    private LocalDate expiryDate;
}
