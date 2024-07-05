package com.dinitProject.healthcheckMonitoringApp.services;

import com.dinitProject.healthcheckMonitoringApp.dtos.UrlDtoAdd;
import com.dinitProject.healthcheckMonitoringApp.dtos.UrlDtoGet;
import com.dinitProject.healthcheckMonitoringApp.dtos.UrlResponseDto;
import com.dinitProject.healthcheckMonitoringApp.dtos.UrlUpdateDto;
import com.dinitProject.healthcheckMonitoringApp.exceptions.conflict.DisplayNameConflictException;
import com.dinitProject.healthcheckMonitoringApp.exceptions.conflict.UrlConflictException;
import com.dinitProject.healthcheckMonitoringApp.exceptions.notfound.UrlNotFoundException;
import com.dinitProject.healthcheckMonitoringApp.models.URLInfo;
import com.dinitProject.healthcheckMonitoringApp.repositorys.UrlRepository;
import jakarta.transaction.Transactional;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class UrlService {

    private final UrlRepository urlRepository;

    @Autowired
    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public List<UrlDtoGet> getUrls() {
        List<UrlDtoGet> list = new ArrayList<>();
        List<URLInfo> urls = urlRepository.findAll();
        for (URLInfo urlInfo : urls)
            list.add(UrlDtoGet.toUrlDto(urlInfo));
        return list;
    }

    public List<URLInfo> getFullUrls() {
        return urlRepository.findAll();
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

    public int getStatusFromUrl(String urlText) throws IOException, URISyntaxException {
        URL url = new URI(urlText).toURL();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        int responseCode = con.getResponseCode();
        con.disconnect();
        return responseCode;
    }

    public boolean checkIfHealthy(String urlStr, int status) {
        try(HttpClient httpClient = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(urlStr))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(response.body());
            } catch (JSONException e) {
                System.out.println("Not the right json structure");
                return false;
            }
            UrlResponseDto urlResponseDto = UrlResponseDto.fromJson(jsonObject);
            System.out.println(urlResponseDto);
            return urlResponseDto.getStatus().equals("Healthy") &&
                    status == HttpURLConnection.HTTP_OK;
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
