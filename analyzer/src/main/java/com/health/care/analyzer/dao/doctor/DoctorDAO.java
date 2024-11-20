package com.health.care.analyzer.dao.doctor;

import com.health.care.analyzer.entity.userEntity.Doctor;
import com.health.care.analyzer.entity.userEntity.User;

import java.util.Optional;

public interface DoctorDAO {
    Doctor save(Doctor doctor);
    Optional<Doctor> findByUser(User user);
    void update(Doctor doctor);
}
