package com.dinitProject.healthcheckMonitoringApp.exceptions.conflict;

public class DisplayNameConflictException extends ConflictException {

    public DisplayNameConflictException() {
        super("Display name already exists");
    }

}
