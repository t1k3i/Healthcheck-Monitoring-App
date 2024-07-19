package com.dinit.healthcheck.exceptions.notfound;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException() {
        super("User not found");
    }
}
