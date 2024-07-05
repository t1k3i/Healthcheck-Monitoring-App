package com.dinitProject.healthcheckMonitoringApp.exceptions;

public class UrlConflictException extends ConflictException {

    public UrlConflictException() {
        super("Url already exists");
    }

}
