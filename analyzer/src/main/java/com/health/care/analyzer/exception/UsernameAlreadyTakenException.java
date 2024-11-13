package com.health.care.analyzer.exception;

public class UsernameAlreadyTakenException extends Exception {
    public UsernameAlreadyTakenException() {
        super();
    }

    public UsernameAlreadyTakenException(String message) {
        super(message);
    }

    public UsernameAlreadyTakenException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameAlreadyTakenException(Throwable cause) {
        super(cause);
    }
}
