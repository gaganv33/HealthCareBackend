package com.health.care.analyzer.dao.prescription;

import com.health.care.analyzer.entity.Prescription;
import com.health.care.analyzer.entity.userEntity.Receptionist;

import java.util.List;
import java.util.Optional;

public interface PrescriptionDAO {
    List<Prescription> getNotAssignedPrescription();
    Optional<Prescription> getPrescriptionById(Long id);
    void merge(Prescription prescription);
    Optional<Prescription> getPrescriptionUsingIdAndReceptionist(Long id, Receptionist receptionist);
    List<Prescription> getPrescriptionUsingReceptionist(Receptionist receptionist);
}
