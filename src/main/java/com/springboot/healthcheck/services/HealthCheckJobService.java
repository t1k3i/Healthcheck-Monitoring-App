package com.springboot.healthcheck.services;

import com.springboot.healthcheck.dtos.UrlResponseDto;
import com.springboot.healthcheck.exceptions.notfound.UrlNotFoundException;
import com.springboot.healthcheck.models.AlertMail;
import com.springboot.healthcheck.models.HealthCheckHistory;
import com.springboot.healthcheck.models.URLInfo;
import com.springboot.healthcheck.repositories.HealthCheckHistoryRepository;
import com.springboot.healthcheck.repositories.UrlRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.net.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Service
public class HealthCheckJobService {
    private final EmailService emailService;
    private final UrlRepository urlRepository;
    private final HealthCheckHistoryRepository healthCheckHistoryRepository;
    private final RestClient restClient;

    @Value("${app.timezone}")
    private String timeZone;
    private ZoneId zoneId;

    Logger logger = Logger.getLogger(getClass().getName());

    public HealthCheckJobService(EmailService emailService, UrlRepository urlRepository, HealthCheckHistoryRepository healthCheckHistoryRepository) {
        this.emailService = emailService;
        this.urlRepository = urlRepository;
        this.healthCheckHistoryRepository = healthCheckHistoryRepository;
        this.restClient = RestClient.builder().build();
    }

    @PostConstruct
    private void init() {
        this.zoneId = ZoneId.of(timeZone);
    }

    @Transactional
    @Scheduled(fixedDelayString = "${fixed.delay}")
    public void updateDB() throws IOException {
        List<URLInfo> urls = urlRepository.findAllWithAlertMails();
        LocalDateTime now = LocalDateTime.now(zoneId).withSecond(0).withNano(0);
        for (URLInfo url : urls) if (!url.isMute()) {
            if (url.getLastChecked() == null) {
                performHealthcheckNow(url);
                continue;
            }
            long diff = Duration.between(url.getLastChecked().withSecond(0), now).toMinutes();
            if (diff >= url.getFrequency()) {
                performHealthcheckNow(url);
            }
        }
    }

    @Transactional
    public void performHealthcheckNow(Long urlId) throws IOException {
        URLInfo url = this.urlRepository.findById(urlId).orElse(null);
        if (url == null)
            throw new UrlNotFoundException();
        performHealthcheckNow(url);
    }

    @Transactional
    public void performHealthcheckNow(URLInfo url) throws IOException {
        if (url.isMute()) {
            logger.info("Url is muted");
            return;
        }
        int newStatus = getStatusFromUrl(url.getUrl());
        boolean newHealthy = checkIfHealthy(url.getUrl(), newStatus);
        Set<AlertMail> listOfMails = url.getAlertMails();
        url.setStatus(newStatus);
        url.setHealthy(newHealthy);
        LocalDateTime now = LocalDateTime.now(zoneId).withNano(0);
        url.setLastChecked(now);
        HealthCheckHistory history = new HealthCheckHistory(url, newHealthy, now);
        healthCheckHistoryRepository.save(history);
        if (!newHealthy && !listOfMails.isEmpty()) {
            sendMail(url, listOfMails);
            logger.info("Emails sent");
        }
    }

    private int getStatusFromUrl(String urlText) throws IOException {
        try {
            URL url = new URI(urlText).toURL();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            int responseCode = con.getResponseCode();
            con.disconnect();
            return responseCode;
        } catch (URISyntaxException | ClassCastException | UnknownHostException |
                 SocketException | MalformedURLException | SocketTimeoutException  e) {
            logger.info("Url not valid");
            return -1;
        }
    }

    private boolean checkIfHealthy(String urlStr, int status) {
        if (status == -1)
            return false;

        String result;
        try {
            result = restClient.get()
                    .uri(urlStr)
                    .retrieve()
                    .body(String.class);
        } catch (Exception e) {
            logger.info("Forbidden health check");
            return false;
        }

        if (result == null) {
            logger.info("Not a JSON response");
            return false;
        }

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(result);
        } catch (JSONException e) {
            logger.info("Not the right json structure");
            return false;
        }

        UrlResponseDto urlResponseDto = UrlResponseDto.fromJson(jsonObject);
        return urlResponseDto.getStatus().equals("Healthy") &&
                status == HttpURLConnection.HTTP_OK;
    }

    private void sendMail(URLInfo url, Set<AlertMail> listOfMails) {
        for (AlertMail alertMail : listOfMails) {
            String gmail = alertMail.getMail();
            String subject = "Health Check Fail";
            String body = url.getDisplayName() + " is unhealthy!!!\n" +
                    "(" + url.getUrl() + ")";
            emailService.sendEmail(gmail, subject, body);
        }
    }

}
