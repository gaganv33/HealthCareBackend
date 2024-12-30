package com.health.care.analyzer.dao.appointment;

import com.health.care.analyzer.entity.Appointment;
import com.health.care.analyzer.entity.Feedback;
import com.health.care.analyzer.entity.userEntity.Doctor;
import com.health.care.analyzer.entity.userEntity.Patient;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AppointmentDAO {
    Appointment save(Appointment appointment);
    void merge(Appointment appointment);
    void deleteAppointment(Appointment appointment);
    List<Appointment> getAllAppointmentUsingPatient(Patient patient);
    List<Appointment> getAllAppointmentUsingPatientAndStage(Patient patient, String stage);
    List<Appointment> getAllAppointmentUsingPatientAndDoctor(Patient patient, Doctor doctor);
    List<Appointment> getAllAppointmentUsingPatientDoctorAndStage(Patient patient, Doctor doctor, String stage);
    void updateFeedback(Long id, Feedback feedback);
    Optional<Appointment> getAppointmentUsingPatientAndId(Patient patient, Long id);
    List<Appointment> getAllAppointmentUsingDoctorAndStage(Doctor doctor);
    Optional<Appointment> getAppointmentById(Long id);
    Optional<Appointment> getAppointmentByUsingWithPhlebotomistTest(Long id);
    List<Appointment> getAppointmentUsingDoctorAndStartDate(Doctor doctor, LocalDate startDate);
    List<Appointment> getAppointmentUsingDoctorStartDateAndEndDate(Doctor doctor, LocalDate startDate, LocalDate endDate);
    List<Appointment> getAppointmentUsingDoctor(Doctor doctor);
}
