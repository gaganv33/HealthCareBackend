package com.health.care.analyzer.service.appointment;

import com.health.care.analyzer.dto.appointment.AppointmentResponseDTO;
import com.health.care.analyzer.entity.Appointment;
import com.health.care.analyzer.entity.Feedback;
import com.health.care.analyzer.entity.userEntity.Doctor;
import com.health.care.analyzer.entity.userEntity.Patient;
import com.health.care.analyzer.exception.appointment.InvalidAppointmentIdException;

import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    Appointment save(Appointment appointment);
    void merge(Appointment appointment);
    void deleteAppointmentByPatientAndId(Patient patient, long id) throws InvalidAppointmentIdException;
    List<AppointmentResponseDTO> getAllAppointmentUsingPatient(Patient patient);
    List<AppointmentResponseDTO> getAllAppointmentUsingPatientAndStage(Patient patient, String stage);
    List<AppointmentResponseDTO> getAllAppointmentUsingPatientAndDoctor(Patient patient, Doctor doctor);
    List<AppointmentResponseDTO> getAllAppointmentUsingPatientDoctorAndStage(Patient patient, Doctor doctor, String stage);
    void updateFeedback(Long id, Feedback feedback);
    Optional<Appointment> getAppointmentUsingPatientAndId(Patient patient, Long id);
    List<AppointmentResponseDTO> getAllAppointmentUsingDoctorAndStage(Doctor doctor);
    Optional<Appointment> getAppointmentById(Long id);
}
