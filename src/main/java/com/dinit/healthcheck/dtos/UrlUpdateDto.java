package com.dinit.healthcheck.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UrlUpdateDto {

    @Size(max = 255)
    private final String displayName;

    @Pattern(
            regexp = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",
            message = "Invalid url format"
    )
    @Size(max = 255, message = "Url is to long")
    private final String url;
    @Min(value = 1, message = "Frequency must be 1 or more")
    private final Integer frequency;

    public UrlUpdateDto(String displayName, String url, Integer frequency) {
        this.displayName = displayName;
        this.url = url;
        this.frequency = frequency;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUrl() {
        return url;
    }

    public Integer getFrequency() {
        return frequency;
    }

}
