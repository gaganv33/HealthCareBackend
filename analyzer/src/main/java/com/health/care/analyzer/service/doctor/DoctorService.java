package com.health.care.analyzer.service.doctor;

import com.health.care.analyzer.dto.profile.ProfileResponseDTO;
import com.health.care.analyzer.entity.userEntity.Doctor;
import com.health.care.analyzer.entity.userEntity.User;

public interface DoctorService {
    void save(Doctor doctor);
    void merge(Doctor doctor);
    ProfileResponseDTO getDoctorProfile(User user);
}
