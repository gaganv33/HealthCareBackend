package com.health.care.analyzer.exception;

public class MedicineVendorNotFoundException extends Exception {
    public MedicineVendorNotFoundException() {
        super();
    }

    public MedicineVendorNotFoundException(String message) {
        super(message);
    }

    public MedicineVendorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MedicineVendorNotFoundException(Throwable cause) {
        super(cause);
    }
}
