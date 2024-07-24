package com.dinit.healthcheck.dtos;

import com.dinit.healthcheck.models.URLInfo;
import jakarta.validation.constraints.*;

public class UrlAddDto {

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
    @Size(min = 1, max = 255)
    private String displayName;
    @NotNull(message = "Frequency can not be null")
    @Min(value = 1, message = "Frequency must be 1 or more")
    private Integer frequency;

    public UrlAddDto(String url, String displayName, Integer frequency) {
        this.url = url;
        this.displayName = displayName;
        this.frequency = frequency;
    }

    public String getUrl() {
        return url;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "UrlAddDto{" +
                "url='" + url + '\'' +
                ", displayName='" + displayName + '\'' +
                ", frekvency=" + frequency +
                '}';
    }

    public static URLInfo toEntity(UrlAddDto urlDto){
        return new URLInfo(urlDto.getUrl(), urlDto.getDisplayName(), urlDto.getFrequency());
    }
}
