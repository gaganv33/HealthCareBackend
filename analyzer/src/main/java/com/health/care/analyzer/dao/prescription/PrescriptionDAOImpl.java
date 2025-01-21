package com.health.care.analyzer.dao.prescription;

import com.health.care.analyzer.entity.Prescription;
import com.health.care.analyzer.entity.userEntity.Receptionist;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PrescriptionDAOImpl implements PrescriptionDAO {
    private final EntityManager entityManager;

    @Autowired
    public PrescriptionDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Prescription> getNotAssignedPrescription() {
        TypedQuery<Prescription> query = entityManager.createQuery(
                "from Prescription p join fetch p.appointment where p.receptionist is null",
                Prescription.class
        );
        return query.getResultList();
    }

    @Override
    public Optional<Prescription> getPrescriptionById(Long id) {
        TypedQuery<Prescription> query = entityManager.createQuery(
                "from Prescription p where p.id = :id", Prescription.class
        );
        query.setParameter("id", id);
        List<Prescription> prescriptionList = query.getResultList();
        if (prescriptionList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(prescriptionList.get(0));
    }

    @Override
    public void merge(Prescription prescription) {
        entityManager.merge(prescription);
    }

    @Override
    public Optional<Prescription> getPrescriptionUsingIdAndReceptionist(Long id, Receptionist receptionist) {
        TypedQuery<Prescription> query = entityManager.createQuery(
                "from Prescription p where p.id = :id and p.receptionist = :receptionist",
                Prescription.class
        );
        query.setParameter("id", id);
        query.setParameter("receptionist", receptionist);
        List<Prescription> prescriptionList = query.getResultList();
        if(prescriptionList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(prescriptionList.get(0));
    }

    @Override
    public List<Prescription> getPrescriptionUsingReceptionist(Receptionist receptionist) {
        TypedQuery<Prescription> query = entityManager.createQuery(
                "from Prescription p join fetch p.receptionist where p.receptionist = :receptionist",
                Prescription.class
        );
        query.setParameter("receptionist", receptionist);
        return query.getResultList();
    }
}
