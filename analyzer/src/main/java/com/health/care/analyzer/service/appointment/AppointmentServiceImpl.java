package com.health.care.analyzer.service.appointment;

import com.health.care.analyzer.dao.appointment.AppointmentDAO;
import com.health.care.analyzer.dto.appointment.AppointmentResponseDTO;
import com.health.care.analyzer.entity.Appointment;
import com.health.care.analyzer.entity.Feedback;
import com.health.care.analyzer.entity.userEntity.Doctor;
import com.health.care.analyzer.entity.userEntity.Patient;
import com.health.care.analyzer.exception.appointment.InvalidAppointmentIdException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void deleteAppointmentByPatientAndId(Patient patient, long id) throws InvalidAppointmentIdException {
        Optional<Appointment> appointmentOptional = appointmentDAO.getAppointmentUsingPatientAndId(patient, id);
        if(appointmentOptional.isEmpty()) {
            throw new InvalidAppointmentIdException("Invalid appointment id");
        }
        Appointment appointment = appointmentOptional.get();
        Doctor appointmentDoctor = appointment.getDoctor();
        Patient appointmentPatient = appointment.getPatient();
        appointmentDoctor.removeAppointment(appointment);
        appointmentPatient.removeAppointment(appointment);
        appointmentDAO.deleteAppointment(appointment);
    }

    @Override
    public List<AppointmentResponseDTO> getAllAppointmentUsingPatient(Patient patient) {
        return appointmentDAO.getAllAppointmentUsingPatient(patient).stream().map(AppointmentResponseDTO::new).toList();
    }

    @Override
    public List<AppointmentResponseDTO> getAllAppointmentUsingPatientAndStage(Patient patient, String stage) {
        return appointmentDAO.getAllAppointmentUsingPatientAndStage(patient, stage).
                stream().map(AppointmentResponseDTO::new).toList();
    }

    @Override
    public List<AppointmentResponseDTO> getAllAppointmentUsingPatientAndDoctor(Patient patient, Doctor doctor) {
        return appointmentDAO.getAllAppointmentUsingPatientAndDoctor(patient, doctor).
                stream().map(AppointmentResponseDTO::new).toList();
    }

    @Override
    public List<AppointmentResponseDTO> getAllAppointmentUsingPatientDoctorAndStage(Patient patient, Doctor doctor, String stage) {
        return appointmentDAO.getAllAppointmentUsingPatientDoctorAndStage(patient, doctor, stage).
                stream().map(AppointmentResponseDTO::new).toList();
    }

    @Override
    @Transactional
    public void updateFeedback(Long id, Feedback feedback) {
        appointmentDAO.updateFeedback(id, feedback);
    }

    @Override
    public Optional<Appointment> getAppointmentUsingPatientAndId(Patient patient, Long id) {
        return appointmentDAO.getAppointmentUsingPatientAndId(patient, id);
    }

    @Override
    public List<AppointmentResponseDTO> getAllAppointmentUsingDoctor(Doctor doctor) {
        return appointmentDAO.getAllAppointmentUsingDoctor(doctor).stream().map(AppointmentResponseDTO::new).toList();
    }
}
