package com.derekdileo.expensetrackerrestapi.exceptions;

public class ExpenseNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    // Parameterized Constructor
    public ExpenseNotFoundException(String message) {
        super(message);
    }
}
