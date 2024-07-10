package com.dinit.healthcheck.exceptions.conflict;

public class UrlConflictException extends ConflictException {

    public UrlConflictException() {
        super("Url already exists");
    }

}
