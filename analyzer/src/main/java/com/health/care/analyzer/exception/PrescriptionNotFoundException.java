package com.health.care.analyzer.exception;

public class PrescriptionNotFoundException extends Exception {
    public PrescriptionNotFoundException() {
        super();
    }

    public PrescriptionNotFoundException(String message) {
        super(message);
    }

    public PrescriptionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PrescriptionNotFoundException(Throwable cause) {
        super(cause);
    }
}
