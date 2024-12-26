package com.health.care.analyzer.service.labTestReport;

import com.health.care.analyzer.dto.labTestReport.PendingLabTestReportDTO;
import com.health.care.analyzer.entity.testEntity.LabTestReport;
import com.health.care.analyzer.entity.userEntity.Phlebotomist;

import java.util.List;
import java.util.Optional;

public interface LabTestReportService {
    List<PendingLabTestReportDTO> getAllPendingLabTestReport();
    Optional<LabTestReport> getPendingLabTestReportUsingId(Long id);
    void merge(LabTestReport labTestReport);
    List<PendingLabTestReportDTO> getAllPendingLabTestUsingPhlebotomist(Phlebotomist phlebotomist);
    Optional<LabTestReport> getPendingLabTestReportUsingIdAndPhlebotomist(Long id, Phlebotomist phlebotomist);
}
