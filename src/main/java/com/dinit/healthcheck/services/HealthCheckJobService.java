package com.dinit.healthcheck.services;

import com.dinit.healthcheck.models.AlertMail;
import com.dinit.healthcheck.models.URLInfo;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Service
public class HealthCheckJobService {
    private final UrlService urlService;
    private final EmailService emailService;

    Logger logger = Logger.getLogger(getClass().getName());

    public HealthCheckJobService(UrlService urlService, EmailService emailService) {
        this.urlService = urlService;
        this.emailService = emailService;
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
            Set<AlertMail> listOfMails = url.getAlertMails();
            url.setStatus(newStatus);
            url.setHealthy(newHealthy);
            url.setLastChecked(LocalDateTime.now());
            if (stateChanged(oldStatus, newStatus, oldHealthy, newHealthy) &&
                    !newHealthy && !listOfMails.isEmpty()) {
                sendMail(url, listOfMails);
                logger.info("Emails sent");
            }
        }
    }

    private boolean stateChanged(int oldStatus, int newStatus,
                                 boolean oldHealthy, boolean newHealthy) {
        return oldStatus != newStatus || oldHealthy != newHealthy;
    }

    private void sendMail(URLInfo url, Set<AlertMail> listOfMails) {
        for (AlertMail alertMail : listOfMails) {
            String gmail = alertMail.getMail();
            String subject = "Health Check fail";
            String body = url.getDisplayName() + " just became unhealthy!!!\n" +
                    "(" + url.getUrl() + ")";
            emailService.sendEmail(gmail, subject, body);
        }
    }

}
