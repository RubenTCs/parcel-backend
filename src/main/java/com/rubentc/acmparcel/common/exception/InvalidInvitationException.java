package com.rubentc.acmparcel.common.exception;

public class InvalidInvitationException extends RuntimeException {
    public InvalidInvitationException() {
        super("Invalid Invitation Link");
    }
}
