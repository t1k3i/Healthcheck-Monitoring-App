package com.springboot.healthcheck.dtos;

import com.springboot.healthcheck.models.URLInfo;

import java.time.LocalDateTime;

public class UrlGetDto {

    private String url;
    private String displayName;
    private Boolean healthy;
    private Long id;
    private boolean mute;
    private LocalDateTime lastChecked;
    private Integer frequency;

    public UrlGetDto(String url, String displayName, Boolean healthy, Long id, boolean mute, LocalDateTime lastChecked, Integer frequency) {
        this.id = id;
        this.url = url;
        this.displayName = displayName;
        this.healthy = healthy;
        this.mute = mute;
        this.lastChecked = lastChecked;
        this.frequency = frequency;
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

    public boolean isMute() {
        return mute;
    }

    public LocalDateTime getLastChecked() {
        return lastChecked;
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

    public Boolean isHealthy() {
        return healthy;
    }

    public void setHealthy(Boolean healthy) {
        this.healthy = healthy;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMute(boolean mute) {
        this.mute = mute;
    }

    public void setLastChecked(LocalDateTime lastChecked) {
        this.lastChecked = lastChecked;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "UrlGetDto{" +
                "url='" + url + '\'' +
                ", displayName='" + displayName + '\'' +
                ", healthy=" + healthy +
                ", id=" + id +
                ", mute=" + mute +
                ", frequency=" + frequency +
                '}';
    }

    public static UrlGetDto toUrlDto(URLInfo urlInfo) {
        return new UrlGetDto(urlInfo.getUrl(), urlInfo.getDisplayName(), urlInfo.isHealthy(),
                urlInfo.getId(), urlInfo.isMute(), urlInfo.getLastChecked(), urlInfo.getFrequency());
    }

}
