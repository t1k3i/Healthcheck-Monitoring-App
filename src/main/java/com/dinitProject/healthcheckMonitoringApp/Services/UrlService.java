package com.dinitProject.healthcheckMonitoringApp.Services;

import com.dinitProject.healthcheckMonitoringApp.Models.URLInfo;
import com.dinitProject.healthcheckMonitoringApp.Repositorys.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UrlService {

    private final UrlRepository urlRepository;

    @Autowired
    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public List<URLInfo> getUrls() {
        return urlRepository.findAll();
    }

    public URLInfo getUrl(Long id) {
        return urlRepository.findById(id).orElse(null);
    }

    public void addUrlInfo(URLInfo url) {
        System.out.println(url);
    }

}
