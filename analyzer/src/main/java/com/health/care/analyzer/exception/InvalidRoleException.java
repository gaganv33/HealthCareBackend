package com.health.care.analyzer.exception;

public class InvalidRoleException extends Exception {
    public InvalidRoleException() {
        super();
    }

    public InvalidRoleException(String message) {
        super(message);
    }

    public InvalidRoleException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRoleException(Throwable cause) {
        super(cause);
    }
}
