package com.health.care.analyzer.service.appointment;

import com.health.care.analyzer.dao.appointment.AppointmentDAO;
import com.health.care.analyzer.data.Stage;
import com.health.care.analyzer.dto.appointment.DoctorAppointmentResponseDTO;
import com.health.care.analyzer.dto.appointment.PatientAppointmentResponseDTO;
import com.health.care.analyzer.entity.Appointment;
import com.health.care.analyzer.entity.Feedback;
import com.health.care.analyzer.entity.testEntity.LabTestReport;
import com.health.care.analyzer.entity.testEntity.PhlebotomistTest;
import com.health.care.analyzer.entity.userEntity.Doctor;
import com.health.care.analyzer.entity.userEntity.Patient;
import com.health.care.analyzer.exception.appointment.InvalidAppointmentIdException;
import com.health.care.analyzer.service.phlebotomistTest.PhlebotomistTestService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentDAO appointmentDAO;
    private final PhlebotomistTestService phlebotomistTestService;

    @Autowired
    public AppointmentServiceImpl(AppointmentDAO appointmentDAO,
                                  PhlebotomistTestService phlebotomistTestService) {
        this.appointmentDAO = appointmentDAO;
        this.phlebotomistTestService = phlebotomistTestService;
    }

    @Override
    @Transactional
    public Appointment save(Appointment appointment) {
        return appointmentDAO.save(appointment);
    }

    @Override
    @Transactional
    public void merge(Appointment appointment) {
        appointmentDAO.merge(appointment);
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
    public List<PatientAppointmentResponseDTO> getAllAppointmentUsingPatient(Patient patient) {
        return appointmentDAO.getAllAppointmentUsingPatient(patient).stream().map(PatientAppointmentResponseDTO::new).toList();
    }

    @Override
    public List<PatientAppointmentResponseDTO> getAllAppointmentUsingPatientAndStage(Patient patient, String stage) {
        return appointmentDAO.getAllAppointmentUsingPatientAndStage(patient, stage).
                stream().map(PatientAppointmentResponseDTO::new).toList();
    }

    @Override
    public List<PatientAppointmentResponseDTO> getAllAppointmentUsingPatientAndDoctor(Patient patient, Doctor doctor) {
        return appointmentDAO.getAllAppointmentUsingPatientAndDoctor(patient, doctor).
                stream().map(PatientAppointmentResponseDTO::new).toList();
    }

    @Override
    public List<PatientAppointmentResponseDTO> getAllAppointmentUsingPatientDoctorAndStage(Patient patient, Doctor doctor, String stage) {
        return appointmentDAO.getAllAppointmentUsingPatientDoctorAndStage(patient, doctor, stage).
                stream().map(PatientAppointmentResponseDTO::new).toList();
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
    public List<DoctorAppointmentResponseDTO> getAllAppointmentUsingDoctorAndStage(Doctor doctor) {
        return appointmentDAO.getAllAppointmentUsingDoctorAndStage(doctor).stream().map(DoctorAppointmentResponseDTO::new).toList();
    }

    @Override
    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentDAO.getAppointmentById(id);
    }

    @Override
    public Optional<Appointment> getAppointmentByIdWithPhlebotomistTest(Long id) {
        return appointmentDAO.getAppointmentByUsingWithPhlebotomistTest(id);
    }

    @Override
    public List<DoctorAppointmentResponseDTO> getAppointmentUsingDoctorStartTimeEndTimeAndPatientData(
            Doctor doctor, LocalDate startDate, LocalDate endDate, String patientFirstName, String patientLastName) {
        List<DoctorAppointmentResponseDTO> appointmentList = getAppointmentUsingDoctorAndDate(doctor, startDate, endDate);
        return getAppointmentUsingPatientData(appointmentList, patientFirstName, patientLastName);
    }

    @Override
    public List<DoctorAppointmentResponseDTO> getAppointmentUsingPatientData(
            List<DoctorAppointmentResponseDTO> appointmentList, String patientFirstName, String patientLastName) {
        if(patientFirstName == null && patientLastName == null) {
            return appointmentList;
        } else if(patientFirstName != null && patientLastName != null) {
            return getAppointmentUsingPatientFirstNameAndLastName(appointmentList, patientFirstName, patientLastName);
        } else if(patientFirstName != null) {
            return getAppointmentUsingPatientFirstName(appointmentList, patientFirstName);
        } else {
            return getAppointmentUsingPatientLastName(appointmentList, patientLastName);
        }
    }

    @Override
    public List<DoctorAppointmentResponseDTO> getAppointmentUsingPatientFirstName(
            List<DoctorAppointmentResponseDTO> appointmentList, String patientFirstName) {
        appointmentList = appointmentList.stream().filter((appointment) -> {
            return appointment.getPatientFirstName().equals(patientFirstName);
        }).collect(Collectors.toList());
        return appointmentList;
    }

    @Override
    public List<DoctorAppointmentResponseDTO> getAppointmentUsingPatientLastName(
            List<DoctorAppointmentResponseDTO> appointmentList, String patientLastName) {
        appointmentList = appointmentList.stream().filter((appointment) -> {
            return appointment.getPatientLastName().equals(patientLastName);
        }).collect(Collectors.toList());
        return appointmentList;
    }

    @Override
    public List<DoctorAppointmentResponseDTO> getAppointmentUsingPatientFirstNameAndLastName(
            List<DoctorAppointmentResponseDTO> appointmentList, String patientFirstName, String patientLastName) {
        appointmentList = appointmentList.stream().filter((appointment) -> {
            return (appointment.getPatientFirstName().equals(patientFirstName) && appointment.getPatientLastName().equals(patientLastName));
        }).collect(Collectors.toList());
        return appointmentList;
    }

    @Override
    public List<DoctorAppointmentResponseDTO> getAppointmentUsingDoctorAndDate(Doctor doctor, LocalDate startDate, LocalDate endDate) {
        if(startDate == null && endDate == null) {
            return appointmentDAO.getAppointmentUsingDoctor(doctor).stream().map(DoctorAppointmentResponseDTO::new).collect(Collectors.toList());
        } else if(startDate != null && endDate == null) {
            return getAppointmentUsingDoctorAndStartDate(doctor, startDate);
        } else {
            return getAppointmentUsingDoctorStartDateAndEndDate(doctor, startDate, endDate);
        }
    }

    @Override
    public List<DoctorAppointmentResponseDTO> getAppointmentUsingDoctorAndStartDate(Doctor doctor, LocalDate startDate) {
        return appointmentDAO.getAppointmentUsingDoctorAndStartDate(doctor, startDate)
                .stream().map(DoctorAppointmentResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<DoctorAppointmentResponseDTO> getAppointmentUsingDoctorStartDateAndEndDate(Doctor doctor, LocalDate startDate, LocalDate endDate) {
        return appointmentDAO.getAppointmentUsingDoctorStartDateAndEndDate(doctor, startDate, endDate)
                .stream().map(DoctorAppointmentResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean isUpdatedAppointmentFromPhlebotomistToDoctor(Appointment appointment) {
        if(!Objects.equals(appointment.getStage(), Stage.PHLEBOTOMIST)) {
            return false;
        }
        PhlebotomistTest phlebotomistTest = appointment.getPhlebotomistTest();
        if(phlebotomistTest == null) {
            return false;
        }
        Optional<PhlebotomistTest> optionalPhlebotomistTest = phlebotomistTestService.getPhlebotomistTestUsingIdWithLabTestReports(phlebotomistTest.getId());
        if(optionalPhlebotomistTest.isEmpty()) {
            return false;
        }
        List<LabTestReport> labTestReportList = optionalPhlebotomistTest.get().getLabTestReportList();
        if(!checkIfAllLabTestResultAreUpdated(labTestReportList)) {
            return false;
        }
        appointment.setStage(Stage.DOCTOR);
        appointmentDAO.merge(appointment);
        return true;
    }

    @Override
    public boolean checkIfAllLabTestResultAreUpdated(List<LabTestReport> labTestReportList) {
        for(LabTestReport labTestReport : labTestReportList) {
            if(labTestReport.getResult() == null) {
                return false;
            }
        }
        return true;
    }
}
