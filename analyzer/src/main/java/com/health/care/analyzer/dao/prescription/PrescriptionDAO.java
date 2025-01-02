package com.health.care.analyzer.dao.prescription;

import com.health.care.analyzer.entity.Prescription;

import java.util.List;
import java.util.Optional;

public interface PrescriptionDAO {
    List<Prescription> getNotAssignedPrescription();
    Optional<Prescription> getPrescriptionById(Long id);
    void merge(Prescription prescription);
}
