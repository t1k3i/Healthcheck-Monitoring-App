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
    private int status;
    private LocalDateTime lastChecked;

    public URLInfo(Long id, String url, String displayName,
                   int status, LocalDateTime lastChecked) {
        this.id = id;
        this.url = url;
        this.displayName = displayName;
        this.status = status;
        this.lastChecked = lastChecked;
    }

    public URLInfo() {}

    public URLInfo(String url, String displayName, int status) {
        this.url = url;
        this.displayName = displayName;
        this.status = status;
        this.lastChecked = LocalDateTime.now();
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

    public int getStatus() {
        return this.status;
    }

    public LocalDateTime getLastChecked() {
        return this.lastChecked;
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

    public void setStatus(int status) {
        this.status = status;
    }

    public void setLastChecked(LocalDateTime lastChecked) {
        this.lastChecked = lastChecked;
    }

    @Override
    public String toString() {
        return "URLInfo{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", displayName='" + displayName + '\'' +
                ", status=" + status +
                ", lastChecked=" + lastChecked +
                '}';
    }
}
