package com.health.care.analyzer.dto.medicine;

import com.health.care.analyzer.entity.medicineEntity.AvailableMedicineRecord;
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
public class AvailableMedicineRecordResponseDTO {
    private Long id;
    private String name;
    private Integer quantity;
    private String serialNo;
    private LocalDate expiryDate;

    public AvailableMedicineRecordResponseDTO(AvailableMedicineRecord availableMedicineRecord) {
        this.id = availableMedicineRecord.getId();
        this.name = availableMedicineRecord.getName();
        this.quantity = availableMedicineRecord.getQuantity();
        this.serialNo = availableMedicineRecord.getSerialNo();
        this.expiryDate = availableMedicineRecord.getExpiryDate();
    }

    public AvailableMedicineRecordResponseDTO(Medicine medicine) {
        this.id = medicine.getId();
        this.name = medicine.getName();
        this.quantity = medicine.getQuantity();
        this.serialNo = medicine.getSerialNo();
        this.expiryDate = medicine.getExpiryDate();
    }
}
