package com.health.care.analyzer.service.appointment;

import com.health.care.analyzer.dao.appointment.AppointmentDAO;
import com.health.care.analyzer.dto.appointment.AppointmentResponseDTO;
import com.health.care.analyzer.entity.Appointment;
import com.health.care.analyzer.entity.Feedback;
import com.health.care.analyzer.entity.userEntity.Doctor;
import com.health.care.analyzer.entity.userEntity.Patient;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentDAO appointmentDAO;

    @Autowired
    public AppointmentServiceImpl(AppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
    }

    @Override
    @Transactional
    public Appointment save(Appointment appointment) {
        return appointmentDAO.save(appointment);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        Optional<Appointment> appointmentOptional = appointmentDAO.getById(id);
        if(appointmentOptional.isEmpty()) {
            return;
        }
        Appointment appointment = appointmentOptional.get();
        Doctor doctor = appointment.getDoctor();
        Patient patient = appointment.getPatient();
        doctor.removeAppointment(appointment);
        patient.removeAppointment(appointment);
        appointmentDAO.deleteById(id);
    }

    @Override
    public List<AppointmentResponseDTO> getAllAppointmentUsingPatient(Patient patient) {
        return appointmentDAO.getAllAppointmentUsingPatient(patient).stream().map(appointment -> {
            return AppointmentResponseDTO.builder()
                    .date(appointment.getDate())
                    .stage(appointment.getStage())
                    .time(appointment.getTime())
                    .doctorFirstName(appointment.getDoctor().getUser().getFirstName())
                    .doctorLastName(appointment.getDoctor().getUser().getLastName())
                    .build();
        }).toList();
    }

    @Override
    public List<AppointmentResponseDTO> getAllAppointmentUsingPatientAndStage(Patient patient, String stage) {
        return appointmentDAO.getAllAppointmentUsingPatientAndStage(patient, stage).stream().map(appointment -> {
            return AppointmentResponseDTO.builder()
                    .date(appointment.getDate())
                    .stage(appointment.getStage())
                    .time(appointment.getTime())
                    .doctorFirstName(appointment.getDoctor().getUser().getFirstName())
                    .doctorLastName(appointment.getDoctor().getUser().getLastName())
                    .build();
        }).toList();
    }

    @Override
    public List<AppointmentResponseDTO> getAllAppointmentUsingPatientAndDoctor(Patient patient, Doctor doctor) {
        return appointmentDAO.getAllAppointmentUsingPatientAndDoctor(patient, doctor).stream().map(appointment -> {
            return AppointmentResponseDTO.builder()
                    .date(appointment.getDate())
                    .stage(appointment.getStage())
                    .time(appointment.getTime())
                    .doctorLastName(appointment.getDoctor().getUser().getFirstName())
                    .doctorLastName(appointment.getDoctor().getUser().getLastName())
                    .build();
        }).toList();
    }

    @Override
    public List<AppointmentResponseDTO> getAllAppointmentUsingPatientDoctorAndStage(Patient patient, Doctor doctor, String stage) {
        return appointmentDAO.getAllAppointmentUsingPatientDoctorAndStage(patient, doctor, stage).stream().map(appointment -> {
            return AppointmentResponseDTO.builder()
                    .date(appointment.getDate())
                    .stage(appointment.getStage())
                    .time(appointment.getTime())
                    .doctorFirstName(appointment.getDoctor().getUser().getFirstName())
                    .doctorLastName(appointment.getDoctor().getUser().getLastName())
                    .build();
        }).toList();
    }

    @Override
    @Transactional
    public void updateFeedback(Long id, Feedback feedback) {
        appointmentDAO.updateFeedback(id, feedback);
    }

    @Override
    public Optional<Appointment> getById(Long id) {
        return appointmentDAO.getById(id);
    }

    @Override
    public Optional<Feedback> getAppointmentFeedbackById(Long id) {
        return appointmentDAO.getAppointmentFeedbackById(id);
    }
}
