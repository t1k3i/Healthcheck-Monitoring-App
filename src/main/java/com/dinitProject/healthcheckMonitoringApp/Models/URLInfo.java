package com.dinitProject.healthcheckMonitoringApp.Models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "urlinfo")
public class URLInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String displayName;
    private Integer status;
    private LocalDateTime lastChecked;
    private Boolean healthy;

    public URLInfo() {}

    public URLInfo(String url, String displayName) {
        this.url = url;
        this.displayName = displayName;
    }

    public Long getId() {
        return this.id;
    }

    public String getUrl() {
        return this.url;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public Integer getStatus() {
        return this.status;
    }

    public LocalDateTime getLastChecked() {
        return this.lastChecked;
    }

    public Boolean isHealthy() {
        return healthy;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setLastChecked(LocalDateTime lastChecked) {
        this.lastChecked = lastChecked;
    }

    public void setHealthy(Boolean healthy) {
        this.healthy = healthy;
    }

    @Override
    public String toString() {
        return "URLInfo{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", displayName='" + displayName + '\'' +
                ", status=" + status +
                ", lastChecked=" + lastChecked +
                ", healthy=" + healthy +
                '}';
    }
}
