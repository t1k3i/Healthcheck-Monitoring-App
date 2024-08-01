package com.dinit.healthcheck.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "healthcheckhistory")
public class HealthCheckHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime checked;
    private Boolean healthy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "urlinfo_id")
    @JsonBackReference
    private URLInfo urlInfo;

    public HealthCheckHistory() {}

    public HealthCheckHistory(URLInfo urlInfo, Boolean healthy, LocalDateTime checked) {
        this.urlInfo = urlInfo;
        this.healthy = healthy;
        this.checked = checked;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getChecked() {
        return checked;
    }

    public Boolean getHealthy() {
        return healthy;
    }

    public URLInfo getUrlInfo() {
        return urlInfo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setChecked(LocalDateTime checked) {
        this.checked = checked;
    }

    public void setHealthy(Boolean healthy) {
        this.healthy = healthy;
    }

    public void setUrlInfo(URLInfo urlInfo) {
        this.urlInfo = urlInfo;
    }

    @Override
    public String toString() {
        return "HealthCheckHistory{" +
                "id=" + id +
                ", checked=" + checked +
                ", healthy=" + healthy +
                '}';
    }
}
