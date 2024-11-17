package com.health.care.analyzer.service.doctor;

import com.health.care.analyzer.dao.doctor.DoctorDAO;
import com.health.care.analyzer.entity.users.Doctor;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DoctorServiceImpl implements DoctorService {
    private final DoctorDAO doctorDAO;

    @Autowired
    public DoctorServiceImpl(DoctorDAO doctorDAO) {
        this.doctorDAO = doctorDAO;
    }

    @Override
    @Transactional
    public Doctor save(Doctor doctor) {
        Optional<Doctor> doctorOptional = doctorDAO.findByUser(doctor.getUser());
        if(doctorOptional.isEmpty()) {
            doctorDAO.save(doctor);
        } else {
            doctorDAO.update(doctor);
        }
        return doctor;
    }
}
