package com.health.care.analyzer.dto.medicine;

import com.health.care.analyzer.entity.medicineEntity.MedicineRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicineRecordResponseDTO {
    private Long id;
    private String name;
    private Integer quantity;

    public MedicineRecordResponseDTO(MedicineRecord medicineRecord) {
        this.id = medicineRecord.getId();
        this.name = medicineRecord.getName();
        this.quantity = medicineRecord.getQuantity();
    }
}
