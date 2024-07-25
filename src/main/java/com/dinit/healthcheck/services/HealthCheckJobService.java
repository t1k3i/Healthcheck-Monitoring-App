package com.dinit.healthcheck.services;

import com.dinit.healthcheck.dtos.UrlResponseDto;
import com.dinit.healthcheck.exceptions.notfound.UrlNotFoundException;
import com.dinit.healthcheck.models.AlertMail;
import com.dinit.healthcheck.models.URLInfo;
import com.dinit.healthcheck.repositories.UrlRepository;
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
    private final RestClient restClient;

    @Value("${app.timezone}")
    private String timeZone;
    private ZoneId zoneId;

    Logger logger = Logger.getLogger(getClass().getName());

    public HealthCheckJobService(EmailService emailService, UrlRepository urlRepository) {
        this.emailService = emailService;
        this.urlRepository = urlRepository;
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
            long diff = Duration.between(url.getLastChecked().withSecond(0), now).toMinutes();
            if (diff >= url.getFrequency()) {
                System.out.println("made health check " + url.getDisplayName());
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
        if (url.isMute())
            logger.info("Url is muted");
        Integer oldStatus = url.getStatus();
        Boolean oldHealthy = url.isHealthy();
        int newStatus = getStatusFromUrl(url.getUrl());
        boolean newHealthy = checkIfHealthy(url.getUrl(), newStatus);
        Set<AlertMail> listOfMails = url.getAlertMails();
        url.setStatus(newStatus);
        url.setHealthy(newHealthy);
        url.setLastChecked(LocalDateTime.now(zoneId).withNano(0));
        if ((isNull(oldStatus, oldHealthy) || stateChanged(oldStatus, newStatus, oldHealthy, newHealthy)) &&
                !newHealthy && !listOfMails.isEmpty()) {
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
        } catch (URISyntaxException | ClassCastException | UnknownHostException | MalformedURLException e2) {
            logger.info("Url not valid");
            return -1;
        }
    }

    private boolean checkIfHealthy(String urlStr, int status) {
        if (status == -1)
            return false;

        String result = restClient.get()
                .uri(urlStr)
                .retrieve()
                .body(String.class);

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

    private boolean stateChanged(Integer oldStatus, int newStatus,
                                 Boolean oldHealthy, boolean newHealthy) {
        return (oldStatus != newStatus || oldHealthy != newHealthy);
    }

    private boolean isNull(Integer oldStatus, Boolean oldHealthy) {
        return oldStatus == null || oldHealthy == null;
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
