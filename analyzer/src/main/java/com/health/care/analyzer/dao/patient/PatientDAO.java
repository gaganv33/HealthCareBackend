package com.health.care.analyzer.dao.patient;

import com.health.care.analyzer.entity.userEntity.Patient;
import com.health.care.analyzer.entity.userEntity.User;

import java.util.Optional;

public interface PatientDAO {
    Patient save(Patient patient);
    Optional<Patient> findByUser(User user);
    void update(Patient patient);
    Patient getPatientProfile(User user);
}
