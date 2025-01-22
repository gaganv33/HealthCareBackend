package com.health.care.analyzer.dto.receptionist;

import com.health.care.analyzer.dto.medicine.AvailableMedicineRecordResponseDTO;
import com.health.care.analyzer.dto.medicine.MedicineRecordResponseDTO;
import com.health.care.analyzer.entity.medicineEntity.Medicine;
import com.health.care.analyzer.entity.medicineEntity.MedicineRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OnSavePrescriptionMedicineListDTO {
    private List<MedicineRecordResponseDTO> requiredMedicineList;
    private List<AvailableMedicineRecordResponseDTO> availableMedicineList;
    private List<MedicineRecordResponseDTO> pendingMedicineList;
    private String message;

    public OnSavePrescriptionMedicineListDTO(List<MedicineRecord> requiredMedicineRecordList) {
        requiredMedicineList = requiredMedicineRecordList.stream().map(MedicineRecordResponseDTO::new).toList();
        availableMedicineList = new ArrayList<>();
        pendingMedicineList = new ArrayList<>();
    }

    public void addMedicineRecordToPendingMedicineList(MedicineRecord medicineRecord) {
        pendingMedicineList.add(new MedicineRecordResponseDTO(medicineRecord));
    }

    public void addMedicineRecordToAvailableMedicineList(Medicine medicine) {
        availableMedicineList.add(new AvailableMedicineRecordResponseDTO(medicine));
    }
}
