package com.dinit.healthcheck.services;

import com.dinit.healthcheck.models.URLInfo;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class HealthCheckJob {
    private final UrlService urlService;

    public HealthCheckJob(UrlService urlService) {
        this.urlService = urlService;
    }

    @Transactional
    @Scheduled(fixedDelayString = "${fixed.delay}")
    public void updateDB() throws IOException {
        List<URLInfo> urls = urlService.getFullUrls();
        for (URLInfo url : urls) {
            int oldStatus = url.getStatus();
            boolean oldHealthy = url.isHealthy();
            int newStatus = urlService.getStatusFromUrl(url.getUrl());
            boolean newHealthy = urlService.checkIfHealthy(url.getUrl(), newStatus);
            //Set<AlertMail> listOfMails = url.getAlertMails();
            url.setStatus(newStatus);
            url.setHealthy(newHealthy);
            url.setLastChecked(LocalDateTime.now());
            if (oldStatus != newStatus || oldHealthy != newHealthy) {
                System.out.println("Change!!!!!"); //TODO
            }
        }
    }
}
