package com.dinit.healthcheck.dtos;

import com.dinit.healthcheck.models.URLInfo;

public class UrlDtoGet {

    private String url;
    private String displayName;
    private Boolean healthy;
    private Long id;

    public UrlDtoGet(String url, String displayName, Boolean healthy, Long id) {
        this.id = id;
        this.url = url;
        this.displayName = displayName;
        this.healthy = healthy;
    }

    public Long getId() {
        return id;
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

    public Boolean isHealthy() {
        return healthy;
    }

    public void setHealthy(Boolean healthy) {
        this.healthy = healthy;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UrlDtoGet{" +
                "url='" + url + '\'' +
                ", displayName='" + displayName + '\'' +
                ", healthy=" + healthy +
                '}';
    }

    public static UrlDtoGet toUrlDto(URLInfo urlInfo) {
        return new UrlDtoGet(urlInfo.getUrl(), urlInfo.getDisplayName(), urlInfo.isHealthy(), urlInfo.getId());
    }

}
