package com.health.care.analyzer.dao.appointment;

import com.health.care.analyzer.entity.Appointment;
import com.health.care.analyzer.entity.userEntity.Patient;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AppointmentDAOImpl implements AppointmentDAO {
    private final EntityManager entityManager;

    @Autowired
    public AppointmentDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Appointment save(Appointment appointment) {
        entityManager.persist(appointment);
        return appointment;
    }

    @Override
    public Optional<Appointment> getById(long id) {
        TypedQuery<Appointment> query = entityManager.createQuery("from Appointment a where a.id = :id", Appointment.class);
        query.setParameter("id", id);
        List<Appointment> appointmentList = query.getResultList();
        if(appointmentList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(appointmentList.get(0));
    }

    @Override
    public void deleteById(long id) {
        entityManager.createQuery("delete from Appointment a where a.id = :id")
                .setParameter("id", id).executeUpdate();
    }

    @Override
    public List<Appointment> getAllAppointmentUsingPatient(Patient patient) {
        TypedQuery<Appointment> query = entityManager.createQuery("from Appointment a where a.patient = :patient",
                Appointment.class);
        query.setParameter("patient", patient);
        return query.getResultList();
    }

    @Override
    public List<Appointment> getAllAppointmentUsingPatientAndStage(Patient patient, String stage) {
        TypedQuery<Appointment> query = entityManager.createQuery("from Appointment a where a.patient = :patient and a.stage = :stage",
                Appointment.class);
        query.setParameter("patient", patient);
        query.setParameter("stage", stage);
        return query.getResultList();
    }
}
