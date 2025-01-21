package com.health.care.analyzer.dto.prescription;

import com.health.care.analyzer.dto.medicine.AvailableMedicineRecordResponseDTO;
import com.health.care.analyzer.dto.medicine.MedicineRecordResponseDTO;
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
    private Long id;
    private String receptionistUsername;
    private List<MedicineRecordResponseDTO> requiredMedicine;
    private List<AvailableMedicineRecordResponseDTO> availableMedicine;
    private List<MedicineRecordResponseDTO> pendingMedicine;

    public PrescriptionResponseDTO(Prescription prescription) {
        this.id = prescription.getId();
        if(prescription.getReceptionist() != null) {
            this.receptionistUsername = prescription.getReceptionist().getUser().getUsername();
        }
        requiredMedicine = prescription.getRequiredMedicineList().stream().map(MedicineRecordResponseDTO::new).toList();
        availableMedicine = prescription.getAvailableMedicineList().stream().map(AvailableMedicineRecordResponseDTO::new).toList();
        pendingMedicine = prescription.getPendingMedicineList().stream().map(MedicineRecordResponseDTO::new).toList();
    }
}
