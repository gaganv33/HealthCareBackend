package com.health.care.analyzer.service.phlebotomistTest;

import com.health.care.analyzer.dao.phlebotomistTest.PhlebotomistTestDAO;
import com.health.care.analyzer.entity.testEntity.PhlebotomistTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PhlebotomistTestServiceImpl implements PhlebotomistTestService {
    private final PhlebotomistTestDAO phlebotomistTestDAO;

    @Autowired
    public PhlebotomistTestServiceImpl(PhlebotomistTestDAO phlebotomistTestDAO) {
        this.phlebotomistTestDAO = phlebotomistTestDAO;
    }

    @Override
    public Optional<PhlebotomistTest> getPhlebotomistTestUsingIdWithLabTestReports(Long id) {
        return phlebotomistTestDAO.getPhlebotomistTestUsingIdWithLabTestReports(id);
    }
}
