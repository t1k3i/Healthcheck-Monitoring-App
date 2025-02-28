package com.springboot.healthcheck.dtos;

import com.springboot.healthcheck.models.HealthCheckHistory;

import java.time.LocalDateTime;

public class HistoryDto {

    private LocalDateTime checked;
    private Boolean healthy;

    public HistoryDto(LocalDateTime checked, Boolean healthy) {
        this.checked = checked;
        this.healthy = healthy;
    }

    public LocalDateTime getChecked() {
        return checked;
    }

    public Boolean getHealthy() {
        return healthy;
    }

    public void setChecked(LocalDateTime checked) {
        this.checked = checked;
    }

    public void setHealthy(Boolean healthy) {
        this.healthy = healthy;
    }

    @Override
    public String toString() {
        return "HistoryDto{" +
                "checked=" + checked +
                ", healthy=" + healthy +
                '}';
    }

    public static HistoryDto toDto(HealthCheckHistory healthCheckHistory) {
        return new HistoryDto(healthCheckHistory.getChecked(), healthCheckHistory.getHealthy());
    }
}
