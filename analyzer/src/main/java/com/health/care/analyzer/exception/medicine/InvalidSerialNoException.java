package com.health.care.analyzer.exception.medicine;

public class InvalidSerialNoException extends Exception {
    public InvalidSerialNoException() {
        super();
    }

    public InvalidSerialNoException(String message) {
        super(message);
    }

    public InvalidSerialNoException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidSerialNoException(Throwable cause) {
        super(cause);
    }
}
