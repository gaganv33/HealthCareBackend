package com.health.care.analyzer.service.phlebotomistTest;

import com.health.care.analyzer.entity.testEntity.PhlebotomistTest;

import java.util.Optional;

public interface PhlebotomistTestService {
    Optional<PhlebotomistTest> getPhlebotomistTestUsingIdWithLabTestReports(Long id);
}
