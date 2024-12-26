package com.health.care.analyzer.dao.labTestReport;

import com.health.care.analyzer.entity.testEntity.LabTestReport;

import java.util.List;

public interface LabTestReportDAO {
    List<LabTestReport> getAllPendingLabTestReport();
}
