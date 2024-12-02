package com.health.care.analyzer.dao.doctor;

import com.health.care.analyzer.entity.Designation;
import com.health.care.analyzer.entity.userEntity.Doctor;
import com.health.care.analyzer.entity.userEntity.User;

import java.util.List;
import java.util.Optional;

public interface DoctorDAO {
    Doctor save(Doctor doctor);
    Doctor merge(Doctor doctor);
    Optional<Doctor> findByUser(User user);
    void update(Doctor doctor);
    Doctor getDoctorProfile(User user);
}
