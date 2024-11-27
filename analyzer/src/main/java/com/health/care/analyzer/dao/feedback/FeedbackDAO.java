package com.health.care.analyzer.dao.feedback;

import com.health.care.analyzer.entity.Feedback;

public interface FeedbackDAO {
    Feedback save(Feedback feedback);
    void update(Feedback feedback);
}
