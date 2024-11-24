package com.health.care.analyzer.dao.doctor;

import com.health.care.analyzer.entity.userEntity.Doctor;
import com.health.care.analyzer.entity.userEntity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DoctorDAOImpl implements DoctorDAO {
    private final EntityManager entityManager;

    @Autowired
    public DoctorDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Doctor save(Doctor doctor) {
        entityManager.persist(doctor);
        return doctor;
    }

    @Override
    public Optional<Doctor> findByUser(User user) {
        TypedQuery<Doctor> query = entityManager.createQuery("from Doctor d where d.user = :user",
                Doctor.class);
        query.setParameter("user", user);
        List<Doctor> doctorList = query.getResultList();
        if(doctorList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(doctorList.get(0));
    }

    @Override
    public void update(Doctor doctor) {
        entityManager.createQuery(
                "update Doctor d set d.dob = :dob, d.weight = :weight, d.height = :height" +
                        "d.phoneNo = :phoneNo, d.bloodGroup = :bloodGroup where d.user = :user")
                        .setParameter("dob", doctor.getDob())
                        .setParameter("weight", doctor.getWeight())
                        .setParameter("height", doctor.getHeight())
                        .setParameter("phoneNo", doctor.getPhoneNo())
                        .setParameter("bloodGroup", doctor.getBloodGroup())
                        .setParameter("user", doctor.getUser())
                        .executeUpdate();
    }
}
