package com.health.care.analyzer.service.prescription;

import com.health.care.analyzer.dto.prescription.NotAssignedPrescription;
import com.health.care.analyzer.dto.receptionist.OnSavePrescriptionMedicineListDTO;
import com.health.care.analyzer.entity.Prescription;
import com.health.care.analyzer.entity.userEntity.Receptionist;

import java.util.List;
import java.util.Optional;

public interface PrescriptionService {
    List<NotAssignedPrescription> getNotAssignedPrescription();
    Optional<Prescription> getPrescriptionById(Long id);
    boolean updatePrescriptionUsingIdAndReceptionist(Long id, Receptionist receptionist);
    Optional<Prescription> getPrescriptionUsingIdAndReceptionist(Long id, Receptionist receptionist);
    OnSavePrescriptionMedicineListDTO onSavePrescriptionMedicineList(Prescription prescription);
    List<Prescription> getPrescriptionUsingReceptionist(Receptionist receptionist);
}
