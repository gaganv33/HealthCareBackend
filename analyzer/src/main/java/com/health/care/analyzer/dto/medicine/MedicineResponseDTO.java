package com.health.care.analyzer.dto.medicine;

import com.health.care.analyzer.entity.medicineEntity.Medicine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicineResponseDTO {
    private String name;
    private Integer quantity;
    private String serialNo;
    private LocalDate expiryDate;

    public MedicineResponseDTO(Medicine medicine) {
        this.name = medicine.getName();
        this.quantity = medicine.getQuantity();
        this.serialNo = medicine.getSerialNo();
        this.expiryDate = medicine.getExpiryDate();
    }
}
