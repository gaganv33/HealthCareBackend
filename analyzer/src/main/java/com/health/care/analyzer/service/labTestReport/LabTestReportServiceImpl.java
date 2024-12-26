package com.health.care.analyzer.service.labTestReport;

import com.health.care.analyzer.dao.labTestReport.LabTestReportDAO;
import com.health.care.analyzer.dto.labTestReport.PendingLabTestReportDTO;
import com.health.care.analyzer.entity.testEntity.LabTestReport;
import com.health.care.analyzer.entity.userEntity.Phlebotomist;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LabTestReportServiceImpl implements LabTestReportService {
    private final LabTestReportDAO labTestReportDAO;

    @Autowired
    public LabTestReportServiceImpl(LabTestReportDAO labTestReportDAO) {
        this.labTestReportDAO = labTestReportDAO;
    }

    @Override
    public List<PendingLabTestReportDTO> getAllPendingLabTestReport() {
        return labTestReportDAO.getAllPendingLabTestReport()
                .stream().map(PendingLabTestReportDTO::new).collect(Collectors.toList());
    }

    @Override
    public Optional<LabTestReport> getPendingLabTestReportUsingId(Long id) {
        return labTestReportDAO.getPendingLabTestReportUsingId(id);
    }

    @Override
    @Transactional
    public void merge(LabTestReport labTestReport) {
        labTestReportDAO.merge(labTestReport);
    }

    @Override
    public List<PendingLabTestReportDTO> getAllPendingLabTestUsingPhlebotomist(Phlebotomist phlebotomist) {
        return labTestReportDAO.getAllPendingLabTestUsingPhlebotomist(phlebotomist)
                .stream().map(PendingLabTestReportDTO::new).collect(Collectors.toList());
    }

    @Override
    public Optional<LabTestReport> getPendingLabTestReportUsingIdAndPhlebotomist(Long id, Phlebotomist phlebotomist) {
        return labTestReportDAO.getPendingLabTestReportUsingIdAndPhlebotomist(id, phlebotomist);
    }
}
