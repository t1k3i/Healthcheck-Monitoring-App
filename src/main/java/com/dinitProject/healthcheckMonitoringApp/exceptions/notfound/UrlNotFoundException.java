package com.dinitProject.healthcheckMonitoringApp.exceptions.notfound;

public class UrlNotFoundException extends NotFoundException {

    public UrlNotFoundException() {
        super("Url not found");
    }

}
