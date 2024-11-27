package com.health.care.analyzer.service.appointment;

import com.health.care.analyzer.dto.appointment.AppointmentResponseDTO;
import com.health.care.analyzer.entity.Appointment;
import com.health.care.analyzer.entity.Feedback;
import com.health.care.analyzer.entity.userEntity.Doctor;
import com.health.care.analyzer.entity.userEntity.Patient;

import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    Appointment save(Appointment appointment);
    void deleteById(long id);
    List<AppointmentResponseDTO> getAllAppointmentUsingPatient(Patient patient);
    List<AppointmentResponseDTO> getAllAppointmentUsingPatientAndStage(Patient patient, String stage);
    List<AppointmentResponseDTO> getAllAppointmentUsingPatientAndDoctor(Patient patient, Doctor doctor);
    List<AppointmentResponseDTO> getAllAppointmentUsingPatientDoctorAndStage(Patient patient, Doctor doctor, String stage);
    void updateFeedback(Long id, Feedback feedback);
    Optional<Appointment> getById(Long id);
    Optional<Feedback> getAppointmentFeedbackById(Long id);
}
