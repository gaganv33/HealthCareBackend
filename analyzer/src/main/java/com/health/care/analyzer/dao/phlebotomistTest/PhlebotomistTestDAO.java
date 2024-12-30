package com.health.care.analyzer.dao.phlebotomistTest;

import com.health.care.analyzer.entity.testEntity.PhlebotomistTest;

import java.util.Optional;

public interface PhlebotomistTestDAO {
    Optional<PhlebotomistTest> getPhlebotomistTestUsingIdWithLabTestReports(Long id);
}
