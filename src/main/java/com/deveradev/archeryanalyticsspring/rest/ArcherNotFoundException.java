package com.deveradev.archeryanalyticsspring.rest;

public class ArcherNotFoundException extends RuntimeException {
    public ArcherNotFoundException(String message) {
        super(message);
    }

    public ArcherNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArcherNotFoundException(Throwable cause) {
        super(cause);
    }
}
