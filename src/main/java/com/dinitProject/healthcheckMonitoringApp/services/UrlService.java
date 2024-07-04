package com.dinitProject.healthcheckMonitoringApp.services;

import com.dinitProject.healthcheckMonitoringApp.dtos.UrlDtoAdd;
import com.dinitProject.healthcheckMonitoringApp.dtos.UrlDtoGet;
import com.dinitProject.healthcheckMonitoringApp.dtos.UrlResponseDto;
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
        for (URLInfo urlInfo : urlRepository.findAll())
            list.add(UrlDtoGet.toUrlDto(urlInfo));
        return list;
    }

    public List<URLInfo> getFullUrls() {
        return new ArrayList<>(urlRepository.findAll());
    }

    public UrlDtoGet getUrl(Long id) {
        URLInfo urlInfo = urlRepository.findById(id).orElse(null);
        return urlInfo != null ? UrlDtoGet.toUrlDto(urlInfo) : null;
    }

    public void addUrlInfo(UrlDtoAdd url) throws IOException {
        if (urlOrNameExists(url.getUrl(), url.getDisplayName()))
            throw new IllegalArgumentException("Url already exists");
        var urlInfo = UrlDtoAdd.toEntity(url);
        urlRepository.save(urlInfo);
    }

    private boolean urlOrNameExists(String url, String displayName) {
        return urlRepository.findByUrl(url).isPresent() ||
                displayNameExists(displayName);
    }

    private boolean displayNameExists(String displayName) {
        return urlRepository.findByDisplayName(displayName).isPresent();
    }

    public void deleteUrl(Long userId) {
        if (!urlRepository.existsById(userId))
            throw new IllegalArgumentException("Url does not exist");
        urlRepository.deleteById(userId);
    }

    public void deleteAll() {
        urlRepository.deleteAll();
    }

    @Transactional
    public void updateDisplayName(Long userId, String newDisplayName) {
        URLInfo urlInfo = urlRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("Url does not exist"));
        if (displayNameExists(newDisplayName))
            throw new IllegalArgumentException("Display name already present");
        urlInfo.setDisplayName(newDisplayName);
    }

    public int getStatusFromUrl(String urlText) throws IOException {
        URL url = new URL(urlText);
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
