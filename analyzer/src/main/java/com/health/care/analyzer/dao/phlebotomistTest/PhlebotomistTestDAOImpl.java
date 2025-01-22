package com.health.care.analyzer.dao.phlebotomistTest;

import com.health.care.analyzer.entity.testEntity.PhlebotomistTest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PhlebotomistTestDAOImpl implements PhlebotomistTestDAO {
    private final EntityManager entityManager;

    @Autowired
    public PhlebotomistTestDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<PhlebotomistTest> getPhlebotomistTestUsingIdWithLabTestReports(Long id) {
        TypedQuery<PhlebotomistTest> query = entityManager.createQuery(
                "from PhlebotomistTest pt where pt.id = :id",
                PhlebotomistTest.class
        );
        query.setParameter("id", id);
        List<PhlebotomistTest> phlebotomistTests = query.getResultList();
        if(phlebotomistTests.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(phlebotomistTests.get(0));
    }
}
