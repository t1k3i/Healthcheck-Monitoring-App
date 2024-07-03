package com.dinitProject.healthcheckMonitoringApp.Dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class UrlDto {

    @NotNull(message = "Url can not be null")
    @NotEmpty(message = "Url can not be empty")
    @Pattern(
            regexp = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",
            message = "Invalid url format"
    )
    private String url;
    @NotNull(message = "Display name can not be null")
    @NotEmpty(message = "Display name can not be empty")
    private String displayName;

    public UrlDto(String url, String displayName) {
        this.url = url;
        this.displayName = displayName;
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

    @Override
    public String toString() {
        return "UrlDto{" +
                "url='" + url + '\'' +
                ", displayName='" + displayName + '\'' +
                '}';
    }

}
