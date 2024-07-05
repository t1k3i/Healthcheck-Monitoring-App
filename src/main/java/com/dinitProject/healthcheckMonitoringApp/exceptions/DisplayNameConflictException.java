package com.dinitProject.healthcheckMonitoringApp.exceptions;

public class DisplayNameConflictException extends ConflictException {

    public DisplayNameConflictException() {
        super("Display name already exists");
    }

}
