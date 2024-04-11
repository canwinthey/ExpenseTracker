package com.xoriant.exception;

public class ExpenseValidationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ExpenseValidationException(String msg) {
        super(msg);
    }
}
