package com.dinitProject.healthcheckMonitoringApp.exceptions.conflict;

import org.springframework.http.HttpStatus;

public abstract class ConflictException extends RuntimeException {

    private final HttpStatus httpStatus;

    public ConflictException(String message) {
        super(message);
        this.httpStatus = HttpStatus.CONFLICT;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
