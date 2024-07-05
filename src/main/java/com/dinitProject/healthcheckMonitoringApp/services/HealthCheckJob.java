package com.dinitProject.healthcheckMonitoringApp.services;

import com.dinitProject.healthcheckMonitoringApp.models.URLInfo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class HealthCheckJob {
    private final UrlService urlService;

    @Autowired
    public HealthCheckJob(UrlService urlService) {
        this.urlService = urlService;
    }

    @Transactional
    @Scheduled(fixedDelayString = "${fixed.delay}")
    public void updateDB() throws IOException {
        List<URLInfo> urls = urlService.getFullUrls();
        for (URLInfo url : urls) {
            int newStatus = urlService.getStatusFromUrl(url.getUrl());
            url.setStatus(newStatus);
            url.setHealthy(urlService.checkIfHealthy(url.getUrl(), newStatus));
            url.setLastChecked(LocalDateTime.now());
        }
    }
}
