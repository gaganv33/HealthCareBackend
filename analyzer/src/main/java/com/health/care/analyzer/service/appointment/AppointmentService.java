package com.health.care.analyzer.service.appointment;

import com.health.care.analyzer.dto.appointment.AppointmentResponseDTO;
import com.health.care.analyzer.entity.Appointment;
import com.health.care.analyzer.entity.userEntity.Patient;

import java.util.List;

public interface AppointmentService {
    Appointment save(Appointment appointment);
    void deleteById(long id);
    List<AppointmentResponseDTO> getAllAppointmentUsingPatient(Patient patient);
    List<AppointmentResponseDTO> getAllAppointmentUsingPatientAndStage(Patient patient, String stage);
}
