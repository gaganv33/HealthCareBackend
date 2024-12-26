package com.health.care.analyzer.service.labTestReport;

import com.health.care.analyzer.dao.labTestReport.LabTestReportDAO;
import com.health.care.analyzer.dto.labTestReport.PendingLabTestReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        return labTestReportDAO.getAllPendingLabTestReport().stream().map(PendingLabTestReportDTO::new).collect(Collectors.toList());
    }
}
