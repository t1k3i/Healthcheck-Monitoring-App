package com.dinit.healthcheck.exceptions.notfound;

public class EmailNotFoundException extends NotFoundException {

    public EmailNotFoundException() {
        super("Email not found");
    }

}
