package com.rubentc.acmparcel.common.exception;

public class PasswordMismatchException extends RuntimeException {
    public PasswordMismatchException() {
        super("Password mismatch");
    }
}
