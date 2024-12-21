package com.health.care.analyzer.utils;

import com.health.care.analyzer.dao.appointment.StageUpdateRequestDTO;
import com.health.care.analyzer.data.UserRole;
import com.health.care.analyzer.entity.Appointment;
import com.health.care.analyzer.entity.userEntity.Doctor;
import com.health.care.analyzer.exception.InvalidOperationException;
import com.health.care.analyzer.exception.appointment.InvalidAppointmentIdException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ValidationHelper {
    public boolean isRoleValid(String role) {
        return role.equals(UserRole.ADMIN) || role.equals(UserRole.PATIENT) || role.equals(UserRole.DOCTOR) ||
                role.equals(UserRole.RECEPTIONIST) || role.equals(UserRole.PHLEBOTOMIST)
                || role.equals(UserRole.MEDICINE_VENDOR);
    }
    public boolean isValidStageUpdateInDoctor(StageUpdateRequestDTO stageUpdateRequestDTO) {
        String stage = stageUpdateRequestDTO.getStage();
        return stage.equals(StageHelper.RECEPTIONIST) || stage.equals(StageHelper.PHLEBOTOMIST) || stage.equals(StageHelper.COMPLETED);
    }

    public Appointment getAppointmentIfNotNull(Optional<Appointment> appointmentOptional)
            throws InvalidAppointmentIdException {
        if(appointmentOptional.isEmpty()) {
            throw new InvalidAppointmentIdException("Invalid appointment id");
        }
        return appointmentOptional.get();
    }

    public void checkAppointmentDoctor(Appointment appointment, Doctor doctor)
            throws InvalidOperationException {
        if(!appointment.getDoctor().getUser().getUsername().equals(doctor.getUser().getUsername())) {
            throw new InvalidOperationException("Doctor cannot access this appointment");
        }
    }

    public void checkAppointmentIsInDoctorStage(Appointment appointment)
            throws InvalidOperationException {
        if(!appointment.getStage().equals(StageHelper.DOCTOR)) {
            throw new InvalidOperationException("The appointment is not in doctor's stage");
        }
    }

    public Appointment getAppointmentAfterChecks(Optional<Appointment> appointmentOptional, Doctor doctor)
            throws InvalidAppointmentIdException, InvalidOperationException {
        Appointment appointment = getAppointmentIfNotNull(appointmentOptional);
        checkAppointmentDoctor(appointment, doctor);
        checkAppointmentIsInDoctorStage(appointment);
        return appointment;
    }
}
