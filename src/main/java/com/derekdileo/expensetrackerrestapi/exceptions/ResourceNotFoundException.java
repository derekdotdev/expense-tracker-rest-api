package com.derekdileo.expensetrackerrestapi.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    // Parameterized Constructor
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
