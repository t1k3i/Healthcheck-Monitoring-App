package com.dinitProject.healthcheckMonitoringApp.Services;

import com.dinitProject.healthcheckMonitoringApp.Dtos.UrlDtoAdd;
import com.dinitProject.healthcheckMonitoringApp.Dtos.UrlDtoGet;
import com.dinitProject.healthcheckMonitoringApp.Models.URLInfo;
import com.dinitProject.healthcheckMonitoringApp.Repositorys.UrlRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UrlService {

    private final UrlRepository urlRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public UrlService(UrlRepository urlRepository, ModelMapper modelMapper) {
        this.urlRepository = urlRepository;
        this.modelMapper = modelMapper;
    }

    public List<UrlDtoGet> getUrls() {
        List<UrlDtoGet> list = new ArrayList<>();
        for (URLInfo urlInfo : urlRepository.findAll())
            list.add(convertToUrlDto(urlInfo));
        return list;
    }

    public UrlDtoGet getUrl(Long id) {
        URLInfo urlInfo = urlRepository.findById(id).orElse(null);
        UrlDtoGet urlDtoGet = urlInfo != null ? convertToUrlDto(urlInfo) : null;
        return urlDtoGet;
    }

    public void addUrlInfo(UrlDtoAdd url) {
        URLInfo urlInfo = convertToURLInfo(url);
        if (!urlRepository.findByUrl(urlInfo.getUrl()).isPresent() &&
                !urlRepository.findByDisplayName(urlInfo.getDisplayName()).isPresent()) {
                urlRepository.save(urlInfo);
        } else {
            throw new IllegalArgumentException("Url already exists");
        }
    }

    public void deleteUrl(Long userId) {
        if (urlRepository.existsById(userId)) {
            urlRepository.deleteById(userId);
        } else {
            throw new IllegalArgumentException("Url does not exist");
        }
    }

    private URLInfo convertToURLInfo(UrlDtoAdd urlDtoAdd) {
        URLInfo urlInfo = new URLInfo();
        urlInfo = modelMapper.map(urlDtoAdd, URLInfo.class);
        urlInfo.setLastChecked(LocalDateTime.now());
        urlInfo.setStatus(200); // TODO
        return urlInfo;
    }

    private UrlDtoGet convertToUrlDto(URLInfo urlInfo) {
        UrlDtoGet urlDtoGet = new UrlDtoGet();
        urlDtoGet = modelMapper.map(urlInfo, UrlDtoGet.class);
        return urlDtoGet;
    }

    @Transactional
    public void updateDisplayName(Long userId, String newDisplayName) {
        URLInfo urlInfo = urlRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("Url does not exist"));
        if (!urlRepository.findByDisplayName(newDisplayName).isPresent()) {
            urlInfo.setDisplayName(newDisplayName);
        } else {
            throw new IllegalArgumentException("Display name already present");
        }
    }
}
