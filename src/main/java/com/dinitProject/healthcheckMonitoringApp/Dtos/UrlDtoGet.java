package com.dinitProject.healthcheckMonitoringApp.Dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class UrlDtoGet {

    private String url;
    private String displayName;
    private Long status;

    public UrlDtoGet() {}

    public UrlDtoGet(String url, String displayName, Long status) {
        this.url = url;
        this.displayName = displayName;
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UrlDtoGet{" +
                "url='" + url + '\'' +
                ", displayName='" + displayName + '\'' +
                ", status=" + status +
                '}';
    }
}
