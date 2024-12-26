package com.health.care.analyzer.dto.labTestReport;

import com.health.care.analyzer.entity.testEntity.LabTestReport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PendingLabTestReportDTO {
    private Long id;
    private Long appointmentId;
    private String name;
    private String details;
    private String patientFirstname;
    private String patientLastName;
    private String doctorFirstName;
    private String doctorLastName;

    public PendingLabTestReportDTO(LabTestReport labTestReport) {
        this.id = labTestReport.getId();
        this.appointmentId = labTestReport.getPhlebotomistTest().getAppointment().getId();
        this.name = labTestReport.getName();
        this.details = labTestReport.getDetails();
        this.patientFirstname = labTestReport.getPhlebotomistTest().getAppointment().getPatient().getUser().getFirstName();
        this.patientLastName = labTestReport.getPhlebotomistTest().getAppointment().getPatient().getUser().getLastName();
        this.doctorFirstName = labTestReport.getPhlebotomistTest().getAppointment().getDoctor().getUser().getFirstName();
        this.doctorLastName = labTestReport.getPhlebotomistTest().getAppointment().getDoctor().getUser().getLastName();
    }
}
