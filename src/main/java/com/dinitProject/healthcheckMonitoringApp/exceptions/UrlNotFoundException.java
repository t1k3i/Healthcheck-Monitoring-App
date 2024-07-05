package com.dinitProject.healthcheckMonitoringApp.exceptions;

public class UrlNotFoundException extends NotFoundException {

    public UrlNotFoundException() {
        super("Url not found");
    }

}
