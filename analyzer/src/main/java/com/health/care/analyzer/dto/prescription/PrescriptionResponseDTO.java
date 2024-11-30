package com.health.care.analyzer.dto.prescription;

import com.health.care.analyzer.dto.medicine.MedicineResponseDTO;
import com.health.care.analyzer.entity.Prescription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionResponseDTO {
    private List<MedicineResponseDTO> medicineList;

    public PrescriptionResponseDTO(Prescription prescription) {
        this.medicineList = prescription.getMedicineList().stream().map(MedicineResponseDTO::new).toList();
    }
}
