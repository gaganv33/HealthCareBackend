package com.health.care.analyzer.exception;

public class PhlebotomistTestResultNotFoundException extends Exception {
    public PhlebotomistTestResultNotFoundException() {
        super();
    }

    public PhlebotomistTestResultNotFoundException(String message) {
        super(message);
    }

    public PhlebotomistTestResultNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PhlebotomistTestResultNotFoundException(Throwable cause) {
        super(cause);
    }
}
