package com.health.care.analyzer.service.feedback;

import com.health.care.analyzer.entity.Feedback;

public interface FeedbackService {
    Feedback save(Feedback feedback);
    Feedback update(Feedback feedback);
}
