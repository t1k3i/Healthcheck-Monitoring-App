package com.dinit.healthcheck.exceptions.notfound;

import org.springframework.http.HttpStatus;

public abstract class NotFoundException extends RuntimeException {

    private final HttpStatus httpStatus;

    protected NotFoundException(String message) {
        super(message);
        this.httpStatus = HttpStatus.NOT_FOUND;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
