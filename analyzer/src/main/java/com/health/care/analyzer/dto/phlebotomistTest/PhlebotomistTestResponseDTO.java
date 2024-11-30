package com.health.care.analyzer.dto.phlebotomistTest;

import com.health.care.analyzer.dto.labTestReport.LabTestReportDTO;
import com.health.care.analyzer.entity.testEntity.PhlebotomistTest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhlebotomistTestResponseDTO {
    private Long id;
    private List<LabTestReportDTO> labTestReportList;

    public PhlebotomistTestResponseDTO(PhlebotomistTest phlebotomistTest) {
        this.id = phlebotomistTest.getId();
        this.labTestReportList = phlebotomistTest.getLabTestReportList().stream().map(LabTestReportDTO::new).toList();
    }
}
