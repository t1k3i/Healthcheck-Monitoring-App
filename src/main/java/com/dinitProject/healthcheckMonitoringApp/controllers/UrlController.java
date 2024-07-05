package com.dinitProject.healthcheckMonitoringApp.controllers;

import com.dinitProject.healthcheckMonitoringApp.dtos.UrlDtoAdd;
import com.dinitProject.healthcheckMonitoringApp.dtos.UrlDtoGet;
import com.dinitProject.healthcheckMonitoringApp.dtos.UrlUpdateDto;
import com.dinitProject.healthcheckMonitoringApp.services.UrlService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "urls")
public class UrlController {

    private final UrlService urlService;

    @Autowired
    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping
    public List<UrlDtoGet> getUrls() {
        return urlService.getUrls();
    }

    @GetMapping("/{urlId}")
    public UrlDtoGet getUrl(@PathVariable("urlId") Long userId) {
        return urlService.getUrl(userId);
    }

    @DeleteMapping("/{urlId}")
    public void deleteUrl(@PathVariable("urlId") Long userId) {
        urlService.deleteUrl(userId);
    }

    @DeleteMapping()
    public void deleteAll() {
        urlService.deleteAll();
    }

    @PostMapping
    public void addURLInfo(@Valid @RequestBody UrlDtoAdd urlInfo) {
        urlService.addUrlInfo(urlInfo);
    }

    @PutMapping("/{urlId}")
    public void updateDisplayName(
            @PathVariable("urlId") Long urlId,
            @Valid @RequestBody UrlUpdateDto urlInfo) {
        urlService.updateDisplayName(urlId, urlInfo);
    }

}