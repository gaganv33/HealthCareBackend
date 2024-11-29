package com.health.care.analyzer.dto.feedback;

import com.health.care.analyzer.entity.Feedback;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class FeedbackResponseDTO {
    private final Long id;
    private final String feedback;

    public FeedbackResponseDTO(Feedback feedback) {
        this.id = feedback.getId();
        this.feedback = feedback.getFeedback();
    }
}
