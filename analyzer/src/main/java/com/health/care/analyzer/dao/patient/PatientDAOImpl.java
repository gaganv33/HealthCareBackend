package com.health.care.analyzer.dao.patient;

import com.health.care.analyzer.entity.userEntity.Patient;
import com.health.care.analyzer.entity.userEntity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PatientDAOImpl implements PatientDAO {
    private final EntityManager entityManager;

    @Autowired
    public PatientDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Patient save(Patient patient) {
        entityManager.persist(patient);
        return patient;
    }

    @Override
    public Optional<Patient> findByUser(User user) {
        TypedQuery<Patient> query = entityManager.createQuery("from Patient p where p.user = :user",
                Patient.class);
        query.setParameter("user", user);
        List<Patient> patientList = query.getResultList();
        if(patientList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(patientList.get(0));
    }

    @Override
    public void update(Patient patient) {
        entityManager.createQuery("update Patient p set p.dob = :dob, p.weight = :weight, p.height = :height" +
                "p.phoneNo = :phoneNo, p.bloodGroup = :bloodGroup where p.user = :user")
                .setParameter("dob", patient.getDob())
                .setParameter("weight", patient.getWeight())
                .setParameter("height", patient.getHeight())
                .setParameter("phoneNo", patient.getPhoneNo())
                .setParameter("bloodGroup", patient.getBloodGroup())
                .setParameter("user", patient.getUser())
                .executeUpdate();
    }

    @Override
    public Patient getPatientProfile(User user) {
        TypedQuery<Patient> query = entityManager.createQuery("from Patient p where p.user = :user", Patient.class);
        query.setParameter("user", user);
        List<Patient> patientList = query.getResultList();
        if(patientList.isEmpty()) {
            return new Patient();
        }
        return patientList.get(0);
    }
}
