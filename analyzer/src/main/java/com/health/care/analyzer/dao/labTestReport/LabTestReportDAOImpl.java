package com.health.care.analyzer.dao.labTestReport;

import com.health.care.analyzer.entity.testEntity.LabTestReport;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LabTestReportDAOImpl implements LabTestReportDAO {
    private final EntityManager entityManager;

    @Autowired
    public LabTestReportDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<LabTestReport> getAllPendingLabTestReport() {
        TypedQuery<LabTestReport> query = entityManager.createQuery(
                "from LabTestReport l where l.phlebotomist is null", LabTestReport.class
        );
        return query.getResultList();
    }
}
