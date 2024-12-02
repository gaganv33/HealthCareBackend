package com.health.care.analyzer.service.doctor;

import com.health.care.analyzer.dao.doctor.DoctorDAO;
import com.health.care.analyzer.dto.profile.ProfileResponseDTO;
import com.health.care.analyzer.entity.userEntity.Doctor;
import com.health.care.analyzer.entity.userEntity.User;
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
    public void save(Doctor doctor) {
        Optional<Doctor> doctorOptional = doctorDAO.findByUser(doctor.getUser());
        if(doctorOptional.isEmpty()) {
            doctorDAO.save(doctor);
        } else {
            doctorDAO.update(doctor);
        }
    }

    @Override
    @Transactional
    public void merge(Doctor doctor) {
        doctorDAO.merge(doctor);
    }

    @Override
    public ProfileResponseDTO getDoctorProfile(User user) {
        Doctor doctor = doctorDAO.getDoctorProfile(user);
        return ProfileResponseDTO.builder()
                .dob(doctor.getDob())
                .phoneNo(doctor.getPhoneNo())
                .bloodGroup(doctor.getBloodGroup())
                .weight(doctor.getWeight())
                .height(doctor.getHeight())
                .build();
    }
}
