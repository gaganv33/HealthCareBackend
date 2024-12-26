package com.health.care.analyzer.service.labTestReport;

import com.health.care.analyzer.dto.labTestReport.PendingLabTestReportDTO;

import java.util.List;

public interface LabTestReportService {
    List<PendingLabTestReportDTO> getAllPendingLabTestReport();
}
