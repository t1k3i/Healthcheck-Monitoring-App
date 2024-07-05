package com.dinitProject.healthcheckMonitoringApp.exceptions.notfound;

import org.springframework.http.HttpStatus;

public abstract class NotFoundException extends RuntimeException {

    private final HttpStatus httpStatus;

    public NotFoundException(String message) {
        super(message);
        this.httpStatus = HttpStatus.NOT_FOUND;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
