package com.dinitProject.healthcheckMonitoringApp.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

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

    @ManyToMany(cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "urlinfo_alertmail",
            joinColumns = @JoinColumn(name = "urlinfo_id"),
            inverseJoinColumns = @JoinColumn(name = "alertmail_id")
    )
    private Set<AlertMail> alertMails;

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

    public Set<AlertMail> getAlertMails() {
        return alertMails;
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

    public void setAlertMails(Set<AlertMail> alertMails) {
        this.alertMails = alertMails;
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
                ", alertMails=" + alertMails +
                '}';
    }
}
