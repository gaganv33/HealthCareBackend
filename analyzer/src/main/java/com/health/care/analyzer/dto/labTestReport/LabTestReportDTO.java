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
public class LabTestReportDTO {
    private String name;
    private String details;
    private String result;

    public LabTestReportDTO(LabTestReport labTestReport) {
        this.name = labTestReport.getName();
        this.result = labTestReport.getResult();
        this.details = labTestReport.getDetails();
    }
}
