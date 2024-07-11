package com.dinit.healthcheck.services;

import com.dinit.healthcheck.dtos.UrlDtoAdd;
import com.dinit.healthcheck.dtos.UrlDtoGet;
import com.dinit.healthcheck.dtos.UrlResponseDto;
import com.dinit.healthcheck.dtos.UrlUpdateDto;
import com.dinit.healthcheck.exceptions.conflict.DisplayNameConflictException;
import com.dinit.healthcheck.exceptions.conflict.EmailConflictException;
import com.dinit.healthcheck.exceptions.conflict.UrlConflictException;
import com.dinit.healthcheck.exceptions.notfound.UrlNotFoundException;
import com.dinit.healthcheck.models.AlertMail;
import com.dinit.healthcheck.models.URLInfo;
import com.dinit.healthcheck.repositorys.UrlRepository;
import jakarta.transaction.Transactional;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private final RestClient restClient;

    Logger logger = Logger.getLogger(getClass().getName());

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
        this.restClient = RestClient.builder().build();
    }

    public List<UrlDtoGet> getUrls() {
        List<UrlDtoGet> list = new ArrayList<>();
        List<URLInfo> urls = urlRepository.findAll();
        for (URLInfo urlInfo : urls)
            list.add(UrlDtoGet.toUrlDto(urlInfo));
        return list;
    }

    public List<URLInfo> getFullUrls() {
        return urlRepository.findAllWithAlertMails();
    }

    public UrlDtoGet getUrl(Long id) {
        URLInfo urlInfo = urlRepository.findById(id).orElse(null);
        if (urlInfo == null)
            throw new UrlNotFoundException();
        return UrlDtoGet.toUrlDto(urlInfo);
    }

    public void deleteUrl(Long urlId) {
        if (!urlRepository.existsById(urlId))
            throw new UrlNotFoundException();
        urlRepository.deleteById(urlId);
    }

    public void deleteAll() {
        urlRepository.deleteAll();
    }

    public void addUrlInfo(UrlDtoAdd url) {
        if (urlExists(url.getUrl()))
            throw new UrlConflictException();
        if (displayNameExists(url.getDisplayName()))
            throw new DisplayNameConflictException();
        var urlInfo = UrlDtoAdd.toEntity(url);
        urlRepository.save(urlInfo);
    }

    private boolean displayNameExists(String displayName) {
        return urlRepository.findByDisplayName(displayName).isPresent();
    }

    private boolean urlExists(String url) {
        return urlRepository.findByUrl(url).isPresent();
    }

    @Transactional
    public void updateDisplayName(Long urlId, UrlUpdateDto newUrlInfo) {
        URLInfo urlInfo = urlRepository.findById(urlId).orElse(null);
        if (urlInfo == null)
            throw new UrlNotFoundException();
        String newDisplayName = newUrlInfo.getDisplayName();
        String newUrl = newUrlInfo.getUrl();
        if (displayNameExists(newDisplayName))
            throw new DisplayNameConflictException();
        if (newUrl != null && urlExists(newUrl))
            throw new UrlConflictException();
        urlInfo.setDisplayName(newDisplayName);
        if (newUrl != null)
            urlInfo.setUrl(newUrl);
    }

    @Transactional
    public void addEmailToUrlInfo(Long urlId, String email) {
        URLInfo urlInfo = urlRepository.findByIdWithAlertMails(urlId).orElse(null);
        if (urlInfo == null)
            throw new UrlNotFoundException();
        AlertMail alertMail = new AlertMail(email);
        if (emailExists(alertMail, urlInfo))
            throw new EmailConflictException();
        urlInfo.getAlertMails().add(alertMail);
    }

    private boolean emailExists(AlertMail alertMail, URLInfo urlInfo) {
        return urlInfo.getAlertMails().contains(alertMail);
    }

    public int getStatusFromUrl(String urlText) throws IOException {
        try {
            URL url = new URI(urlText).toURL();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            int responseCode = con.getResponseCode();
            con.disconnect();
            return responseCode;
        } catch (URISyntaxException e) {
            logger.info("Url not valid");
            return -1;
        }
    }

    public boolean checkIfHealthy(String urlStr, int status) {
        if (status == -1)
            return false;

        String result = restClient.get()
                .uri(urlStr)
                .retrieve()
                .body(String.class);

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
}
