package com.health.care.analyzer.dao.labTestReport;

import com.health.care.analyzer.entity.testEntity.LabTestReport;
import com.health.care.analyzer.entity.userEntity.Phlebotomist;

import java.util.List;
import java.util.Optional;

public interface LabTestReportDAO {
    List<LabTestReport> getAllPendingLabTestReport();
    Optional<LabTestReport> getPendingLabTestReportUsingId(Long id);
    void merge(LabTestReport labTestReport);
    List<LabTestReport> getAllPendingLabTestUsingPhlebotomist(Phlebotomist phlebotomist);
    Optional<LabTestReport> getPendingLabTestReportUsingIdAndPhlebotomist(Long id, Phlebotomist phlebotomist);
}
