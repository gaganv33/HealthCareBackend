package com.health.care.analyzer.service.feedback;

import com.health.care.analyzer.dao.feedback.FeedbackDAO;
import com.health.care.analyzer.entity.Feedback;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackDAO feedbackDAO;

    @Autowired
    public FeedbackServiceImpl(FeedbackDAO feedbackDAO) {
        this.feedbackDAO = feedbackDAO;
    }

    @Override
    @Transactional
    public Feedback save(Feedback feedback) {
        return feedbackDAO.save(feedback);
    }

    @Override
    @Transactional
    public Feedback update(Feedback feedback) {
        feedbackDAO.update(feedback);
        return feedback;
    }
}
