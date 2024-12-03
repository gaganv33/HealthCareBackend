package com.health.care.analyzer.dto.medicine;

import com.health.care.analyzer.entity.medicineEntity.Medicine;
import com.health.care.analyzer.entity.medicineEntity.MedicineRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicineResponseDTO {
    private String name;
    private Integer quantity;
    private LocalDate expiryDate;

    public MedicineResponseDTO(MedicineRecord medicineRecord) {
        this.name = medicineRecord.getName();
        this.quantity = medicineRecord.getQuantity();
    }
}
