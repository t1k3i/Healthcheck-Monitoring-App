package com.dinit.healthcheck.exceptions.conflict;

public class UsernameConflictException extends ConflictException {

    public UsernameConflictException() {
        super("Username already exists");
    }

}
