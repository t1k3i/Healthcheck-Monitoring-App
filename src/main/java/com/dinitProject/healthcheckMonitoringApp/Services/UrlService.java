package com.dinitProject.healthcheckMonitoringApp.Services;

import com.dinitProject.healthcheckMonitoringApp.Dtos.UrlDtoAdd;
import com.dinitProject.healthcheckMonitoringApp.Dtos.UrlDtoGet;
import com.dinitProject.healthcheckMonitoringApp.Models.URLInfo;
import com.dinitProject.healthcheckMonitoringApp.Repositorys.UrlRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
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

    public UrlDtoGet getUrl(Long id) {
        URLInfo urlInfo = urlRepository.findById(id).orElse(null);
        return urlInfo != null ? UrlDtoGet.toUrlDto(urlInfo) : null;
    }

    public void addUrlInfo(UrlDtoAdd url) throws IOException {
        var urlInfo = UrlDtoAdd.toEntity(url);
        if (!urlOrNameExists(url.getUrl(), url.getDisplayName())) {
                urlRepository.save(urlInfo);
                return;
        }
        throw new IllegalArgumentException("Url already exists");
    }

    private boolean urlOrNameExists(String url, String displayName) {
        return urlRepository.findByUrl(url).isPresent() ||
                displayNameExists(displayName);
    }

    private boolean displayNameExists(String displayName) {
        return urlRepository.findByDisplayName(displayName).isPresent();
    }

    public void deleteUrl(Long userId) {
        if (urlRepository.existsById(userId)) {
            urlRepository.deleteById(userId);
            return;
        }
        throw new IllegalArgumentException("Url does not exist");
    }

    public void deleteAll() {
        urlRepository.deleteAll();
    }

    @Transactional
    public void updateDisplayName(Long userId, String newDisplayName) {
        URLInfo urlInfo = urlRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("Url does not exist"));
        if (!displayNameExists(newDisplayName)) {
            urlInfo.setDisplayName(newDisplayName);
            return;
        }
        throw new IllegalArgumentException("Display name already present");
    }

    private int getStatusFromUrl(String urlText) throws IOException {
        URL url = new URL(urlText);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        int responseCode = con.getResponseCode();
        con.disconnect();
        return responseCode;
    }

}
