package com.health.care.analyzer.service.appointment;

import com.health.care.analyzer.dto.appointment.DoctorAppointmentResponseDTO;
import com.health.care.analyzer.dto.appointment.PatientAppointmentResponseDTO;
import com.health.care.analyzer.entity.Appointment;
import com.health.care.analyzer.entity.Feedback;
import com.health.care.analyzer.entity.userEntity.Doctor;
import com.health.care.analyzer.entity.userEntity.Patient;
import com.health.care.analyzer.exception.appointment.InvalidAppointmentIdException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    Appointment save(Appointment appointment);
    void merge(Appointment appointment);
    void deleteAppointmentByPatientAndId(Patient patient, long id) throws InvalidAppointmentIdException;
    List<PatientAppointmentResponseDTO> getAllAppointmentUsingPatient(Patient patient);
    List<PatientAppointmentResponseDTO> getAllAppointmentUsingPatientAndStage(Patient patient, String stage);
    List<PatientAppointmentResponseDTO> getAllAppointmentUsingPatientAndDoctor(Patient patient, Doctor doctor);
    List<PatientAppointmentResponseDTO> getAllAppointmentUsingPatientDoctorAndStage(Patient patient, Doctor doctor, String stage);
    void updateFeedback(Long id, Feedback feedback);
    Optional<Appointment> getAppointmentUsingPatientAndId(Patient patient, Long id);
    List<DoctorAppointmentResponseDTO> getAllAppointmentUsingDoctorAndStage(Doctor doctor);
    Optional<Appointment> getAppointmentById(Long id);
    List<DoctorAppointmentResponseDTO> getAppointmentUsingDoctorStartTimeEndTimeAndPatientData(
            Doctor doctor, LocalDate startDate, LocalDate endDate, String patientFirstName, String patientLastName);
    List<DoctorAppointmentResponseDTO> getAppointmentUsingPatientData(
            List<DoctorAppointmentResponseDTO> appointmentList, String patientFirstName, String patientLastName);
    List<DoctorAppointmentResponseDTO> getAppointmentUsingPatientFirstName(
            List<DoctorAppointmentResponseDTO> appointmentList, String patientFirstName);
    List<DoctorAppointmentResponseDTO> getAppointmentUsingPatientLastName(
            List<DoctorAppointmentResponseDTO> appointmentList, String patientLastName
    );
    List<DoctorAppointmentResponseDTO> getAppointmentUsingPatientFirstNameAndLastName(
            List<DoctorAppointmentResponseDTO> appointmentList, String patientFirstName, String patientLastName
    );
    List<DoctorAppointmentResponseDTO> getAppointmentUsingDoctorAndDate(
            Doctor doctor, LocalDate startDate, LocalDate endDate
    );
    List<DoctorAppointmentResponseDTO> getAppointmentUsingDoctorAndStartDate(Doctor doctor, LocalDate startDate);
    List<DoctorAppointmentResponseDTO> getAppointmentUsingDoctorStartDateAndEndDate(Doctor doctor, LocalDate startDate, LocalDate endDate);

}
