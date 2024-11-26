package com.health.care.analyzer.dao.appointment;

import com.health.care.analyzer.entity.Appointment;
import com.health.care.analyzer.entity.userEntity.Patient;

import java.util.List;
import java.util.Optional;

public interface AppointmentDAO {
    Appointment save(Appointment appointment);
    Optional<Appointment> getById(long id);
    void deleteById(long id);
    List<Appointment> getAllAppointmentUsingPatient(Patient patient);
    List<Appointment> getAllAppointmentUsingPatientAndStage(Patient patient, String stage);
}
