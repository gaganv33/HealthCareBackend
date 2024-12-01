package com.health.care.analyzer.dto.medicineVendor;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicineVendorAddressRequestDTO {
    @NotBlank(message = "address should not be blank")
    private String address;
}
