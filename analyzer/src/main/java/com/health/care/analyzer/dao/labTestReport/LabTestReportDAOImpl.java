package com.health.care.analyzer.dao.labTestReport;

import com.health.care.analyzer.entity.testEntity.LabTestReport;
import com.health.care.analyzer.entity.userEntity.Phlebotomist;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<LabTestReport> getPendingLabTestReportUsingId(Long id) {
        TypedQuery<LabTestReport> query = entityManager.createQuery(
                "from LabTestReport l where l.phlebotomist is null and l.id = :id", LabTestReport.class
        );
        query.setParameter("id", id);
        List<LabTestReport> labTestReportList = query.getResultList();
        if(labTestReportList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(labTestReportList.get(0));
    }

    @Override
    public void merge(LabTestReport labTestReport) {
        entityManager.merge(labTestReport);
    }

    @Override
    public List<LabTestReport> getAllPendingLabTestUsingPhlebotomist(Phlebotomist phlebotomist) {
        TypedQuery<LabTestReport> query = entityManager.createQuery(
                "from LabTestReport l where l.result is null and l.phlebotomist = :phlebotomist",
                LabTestReport.class
        );
        query.setParameter("phlebotomist", phlebotomist);
        return query.getResultList();
    }

    @Override
    public Optional<LabTestReport> getPendingLabTestReportUsingIdAndPhlebotomist(Long id, Phlebotomist phlebotomist) {
        TypedQuery<LabTestReport> query = entityManager.createQuery(
                "from LabTestReport l where l.id = :id and l.phlebotomist = :phlebotomist and l.result is null",
                LabTestReport.class
        );
        query.setParameter("id", id);
        query.setParameter("phlebotomist", phlebotomist);
        List<LabTestReport> labTestReportList = query.getResultList();
        if(labTestReportList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(labTestReportList.get(0));
    }


}
