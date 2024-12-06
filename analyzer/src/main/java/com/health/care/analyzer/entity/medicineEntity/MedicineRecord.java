package com.health.care.analyzer.entity.medicineEntity;

import com.health.care.analyzer.dto.prescription.MedicineRecordResponseDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "medicine_record")
public class MedicineRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    public MedicineRecord(MedicineRecordResponseDTO medicineRecordResponseDTO) {
        this.name = medicineRecordResponseDTO.getName();
        this.quantity = medicineRecordResponseDTO.getQuantity();
    }
}
