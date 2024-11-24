package com.health.care.analyzer.service.patient;

import com.health.care.analyzer.dto.profile.ProfileResponseDTO;
import com.health.care.analyzer.entity.userEntity.Patient;
import com.health.care.analyzer.entity.userEntity.User;

public interface PatientService {
    void save(Patient patient);
    ProfileResponseDTO getPatientProfile(User user);
}
