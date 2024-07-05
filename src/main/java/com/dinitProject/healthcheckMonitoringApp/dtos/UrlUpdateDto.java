package com.dinitProject.healthcheckMonitoringApp.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UrlUpdateDto {

    @NotNull(message = "Url can not be null")
    @NotEmpty(message = "Url can not be empty")
    @Size(min = 1, max = 255)
    private final String displayName;

    @Pattern(
            regexp = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",
            message = "Invalid url format"
    )
    @Size(max = 255, message = "Url is to long")
    private final String url;

    public UrlUpdateDto(String displayName, String url) {
        this.displayName = displayName;
        this.url = url;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUrl() {
        return url;
    }

}
