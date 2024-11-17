package com.health.care.analyzer.dao.doctor;

import com.health.care.analyzer.entity.users.Doctor;
import com.health.care.analyzer.entity.users.User;

import java.util.Optional;

public interface DoctorDAO {
    Doctor save(Doctor doctor);
    Optional<Doctor> findByUser(User user);
    Doctor update(Doctor doctor);
}
