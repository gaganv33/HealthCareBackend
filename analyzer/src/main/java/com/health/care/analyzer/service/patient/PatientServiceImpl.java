package com.health.care.analyzer.service.patient;

import com.health.care.analyzer.dao.patient.PatientDAO;
import com.health.care.analyzer.dto.profile.ProfileResponseDTO;
import com.health.care.analyzer.entity.userEntity.Patient;
import com.health.care.analyzer.entity.userEntity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {
    private final PatientDAO patientDAO;

    @Autowired
    public PatientServiceImpl(PatientDAO patientDAO) {
        this.patientDAO = patientDAO;
    }

    @Override
    @Transactional
    public void save(Patient patient) {
        Optional<Patient> patientOptional = patientDAO.findByUser(patient.getUser());
        if(patientOptional.isEmpty()) {
            patientDAO.save(patient);
        } else {
            patientDAO.update(patient);
        }
    }

    @Override
    public ProfileResponseDTO getPatientProfile(User user) {
        Patient patient = patientDAO.getPatientProfile(user);
        return ProfileResponseDTO.builder()
                .dob(patient.getDob())
                .phoneNo(patient.getPhoneNo())
                .bloodGroup(patient.getBloodGroup())
                .weight(patient.getWeight())
                .height(patient.getHeight())
                .build();
    }
}
