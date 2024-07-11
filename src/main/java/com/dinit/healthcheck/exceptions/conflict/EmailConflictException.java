package com.dinit.healthcheck.exceptions.conflict;

public class EmailConflictException extends ConflictException {

    public EmailConflictException() {
        super("Email already exists for this url");
    }

}
