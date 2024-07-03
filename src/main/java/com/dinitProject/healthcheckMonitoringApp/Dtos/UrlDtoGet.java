package com.dinitProject.healthcheckMonitoringApp.Dtos;

import com.dinitProject.healthcheckMonitoringApp.Models.URLInfo;

public class UrlDtoGet {

    private String url;
    private String displayName;
    private Boolean healthy;

    public UrlDtoGet(String url, String displayName, Boolean healthy) {
        this.url = url;
        this.displayName = displayName;
        this.healthy = healthy;
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

    @Override
    public String toString() {
        return "UrlDtoGet{" +
                "url='" + url + '\'' +
                ", displayName='" + displayName + '\'' +
                ", healthy=" + healthy +
                '}';
    }

    public static UrlDtoGet toUrlDto(URLInfo urlInfo) {
        return new UrlDtoGet(urlInfo.getUrl(), urlInfo.getDisplayName(), urlInfo.isHealthy());
    }

}
