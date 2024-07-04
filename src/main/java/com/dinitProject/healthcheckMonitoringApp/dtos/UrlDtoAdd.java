package com.dinitProject.healthcheckMonitoringApp.dtos;

import com.dinitProject.healthcheckMonitoringApp.models.URLInfo;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UrlDtoAdd {

    @NotNull(message = "Url can not be null")
    @NotEmpty(message = "Url can not be empty")
    @Pattern(
            regexp = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",
            message = "Invalid url format"
    )
    @Size(max = 255, message = "Url is to long")
    private String url;
    @NotNull(message = "Display name can not be null")
    @NotEmpty(message = "Display name can not be empty")
    @Size(min = 3, max = 50, message = "Display name should be between 3 and 50 charachters")
    private String displayName;

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

    public static URLInfo toEntity(UrlDtoAdd urlDto){
        return new URLInfo(urlDto.getUrl(), urlDto.getDisplayName());
    }
}
