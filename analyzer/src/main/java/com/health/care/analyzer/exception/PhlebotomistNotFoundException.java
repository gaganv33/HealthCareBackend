package com.health.care.analyzer.exception;

public class PhlebotomistNotFoundException extends Exception {
    public PhlebotomistNotFoundException() {
        super();
    }

    public PhlebotomistNotFoundException(String message) {
        super(message);
    }

    public PhlebotomistNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PhlebotomistNotFoundException(Throwable cause) {
        super(cause);
    }
}
