package com.health.care.analyzer.exception.appointment;

public class FeedbackNotFoundException extends Exception {
    public FeedbackNotFoundException() {
        super();
    }

    public FeedbackNotFoundException(String message) {
        super(message);
    }

    public FeedbackNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public FeedbackNotFoundException(Throwable cause) {
        super(cause);
    }
}
