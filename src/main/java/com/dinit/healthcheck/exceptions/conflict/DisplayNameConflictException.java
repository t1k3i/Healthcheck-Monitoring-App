package com.dinit.healthcheck.exceptions.conflict;

public class DisplayNameConflictException extends ConflictException {

    public DisplayNameConflictException() {
        super("Display name already exists");
    }

}
