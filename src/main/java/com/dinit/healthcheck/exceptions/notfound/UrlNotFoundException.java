package com.dinit.healthcheck.exceptions.notfound;

public class UrlNotFoundException extends NotFoundException {

    public UrlNotFoundException() {
        super("Url not found");
    }

}
