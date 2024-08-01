package com.dinit.healthcheck.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private boolean mute;
    private Integer frequency;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "urlinfo_alertmail",
            joinColumns = @JoinColumn(name = "urlinfo_id"),
            inverseJoinColumns = @JoinColumn(name = "alertmail_id")
    )
    @JsonManagedReference
    private Set<AlertMail> alertMails;

    @OneToMany(mappedBy = "urlInfo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<HealthCheckHistory> healthCheckHistories;

    public URLInfo() {}

    public URLInfo(String url, String displayName, Integer frequency) {
        this.url = url;
        this.displayName = displayName;
        this.frequency = frequency;
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

    public boolean isMute() {
        return mute;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public Set<AlertMail> getAlertMails() {
        return alertMails;
    }

    public Set<HealthCheckHistory> getHealthCheckHistories() {
        return healthCheckHistories;
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

    public void setMute(boolean mute) {
        this.mute = mute;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public void setAlertMails(Set<AlertMail> alertMails) {
        this.alertMails = alertMails;
    }

    public void setHealthCheckHistories(Set<HealthCheckHistory> healthCheckHistories) {
        this.healthCheckHistories = healthCheckHistories;
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
                ", mute=" + mute +
                ", frequency=" + frequency +
                ", alertMails=" + alertMails +
                ", healthCheckHistories=" + healthCheckHistories +
                '}';
    }
}
