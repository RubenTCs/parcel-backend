package com.rubentc.acmparcel.common.exception;

public class AlreadyExistException extends RuntimeException {
    public AlreadyExistException(String message) {
        super(message + " already exists");
    }
}
