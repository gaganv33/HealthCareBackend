package com.health.care.analyzer.dao.appointment;

import com.health.care.analyzer.entity.Appointment;
import com.health.care.analyzer.entity.Feedback;
import com.health.care.analyzer.entity.userEntity.Doctor;
import com.health.care.analyzer.entity.userEntity.Patient;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
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
    public void merge(Appointment appointment) {
        appointment = entityManager.merge(appointment);
    }

    @Override
    public void deleteAppointment(Appointment appointment) {
        entityManager.remove(appointment);
    }

    @Override
    public List<Appointment> getAllAppointmentUsingPatient(Patient patient) {
        TypedQuery<Appointment> query = entityManager.createQuery(
                "from Appointment a where a.patient = :patient",
                Appointment.class);
        query.setParameter("patient", patient);
        return query.getResultList();
    }

    @Override
    public List<Appointment> getAllAppointmentUsingPatientAndStage(Patient patient, String stage) {
        TypedQuery<Appointment> query = entityManager.createQuery(
                "from Appointment a where a.patient = :patient and a.stage = :stage",
                Appointment.class);
        query.setParameter("patient", patient);
        query.setParameter("stage", stage);
        return query.getResultList();
    }

    @Override
    public List<Appointment> getAllAppointmentUsingPatientAndDoctor(Patient patient, Doctor doctor) {
        TypedQuery<Appointment> query = entityManager.createQuery(
                "from Appointment a where a.patient = :patient and a.doctor = :doctor",
                Appointment.class);
        query.setParameter("patient", patient);
        query.setParameter("doctor", doctor);
        return query.getResultList();
    }

    @Override
    public List<Appointment> getAllAppointmentUsingPatientDoctorAndStage(Patient patient, Doctor doctor, String stage) {
        TypedQuery<Appointment> query = entityManager.createQuery(
                "from Appointment a where a.patient = :patient and a.doctor = :doctor and a.stage = :stage",
                Appointment.class);
        query.setParameter("patient", patient);
        query.setParameter("doctor", doctor);
        query.setParameter("stage", stage);
        return query.getResultList();
    }

    @Override
    public void updateFeedback(Long id, Feedback feedback) {
        entityManager.createQuery("update Appointment a set a.feedback = :feedback where a.id = :id")
                .setParameter("feedback", feedback)
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public Optional<Appointment> getAppointmentUsingPatientAndId(Patient patient, Long id) {
        TypedQuery<Appointment> query = entityManager.createQuery(
                "from Appointment a where a.patient = :patient and a.id = :id",
                Appointment.class);
        query.setParameter("patient", patient);
        query.setParameter("id", id);
        List<Appointment> appointmentList = query.getResultList();
        if(appointmentList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(appointmentList.get(0));
    }

    @Override
    public List<Appointment> getAllAppointmentUsingDoctorAndStage(Doctor doctor) {
        TypedQuery<Appointment> query = entityManager.createQuery(
                "from Appointment a where a.doctor = :doctor and a.stage = :stage",
                Appointment.class);
        query.setParameter("doctor", doctor);
        query.setParameter("stage", "doctor");
        return query.getResultList();
    }

    @Override
    public Optional<Appointment> getAppointmentById(Long id) {
        TypedQuery<Appointment> query = entityManager.createQuery(
                "from Appointment a where a.id = :id",
                Appointment.class);
        query.setParameter("id", id);
        List<Appointment> appointmentList = query.getResultList();
        if(appointmentList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(appointmentList.get(0));
    }
}
