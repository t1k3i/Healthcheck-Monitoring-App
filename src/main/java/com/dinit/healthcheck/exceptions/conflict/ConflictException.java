package com.dinit.healthcheck.exceptions.conflict;

import org.springframework.http.HttpStatus;

public abstract class ConflictException extends RuntimeException {

    private final HttpStatus httpStatus;

    protected ConflictException(String message) {
        super(message);
        this.httpStatus = HttpStatus.CONFLICT;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
