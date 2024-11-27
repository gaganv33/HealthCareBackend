package com.health.care.analyzer.dao.feedback;

import com.health.care.analyzer.entity.Feedback;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FeedbackDAOImpl implements FeedbackDAO {
    private final EntityManager entityManager;

    @Autowired
    public FeedbackDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Feedback save(Feedback feedback) {
        entityManager.persist(feedback);
        return feedback;
    }

    @Override
    public void update(Feedback feedback) {
        entityManager.createQuery("update Feedback f set f.feedback = :feedback where f.id = :id")
                .setParameter("feedback", feedback.getFeedback())
                .setParameter("id", feedback.getId()).executeUpdate();
    }
}
