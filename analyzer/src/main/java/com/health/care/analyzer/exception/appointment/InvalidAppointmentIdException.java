package com.health.care.analyzer.exception.appointment;

public class InvalidAppointmentIdException extends Exception {
    public InvalidAppointmentIdException() {
        super();
    }

    public InvalidAppointmentIdException(String message) {
        super(message);
    }

    public InvalidAppointmentIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidAppointmentIdException(Throwable cause) {
        super(cause);
    }
}
