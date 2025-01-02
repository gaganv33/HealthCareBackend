package com.health.care.analyzer.dto.prescription;

import com.health.care.analyzer.entity.Prescription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotAssignedPrescription {
    private Long id;
    private String doctorFirstName;
    private String doctorLastName;
    private String patientFirstName;
    private String patientLastName;

    public NotAssignedPrescription(Prescription prescription) {
        this.id = prescription.getId();
        this.doctorFirstName = prescription.getAppointment().getDoctor().getUser().getFirstName();
        this.doctorLastName = prescription.getAppointment().getDoctor().getUser().getLastName();
        this.patientFirstName = prescription.getAppointment().getPatient().getUser().getFirstName();
        this.patientLastName = prescription.getAppointment().getPatient().getUser().getLastName();
    }
}
