package com.dinitProject.healthcheckMonitoringApp.controllers;

import com.dinitProject.healthcheckMonitoringApp.dtos.UrlDtoAdd;
import com.dinitProject.healthcheckMonitoringApp.dtos.UrlDtoGet;
import com.dinitProject.healthcheckMonitoringApp.services.UrlService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "url")
public class UrlController {

    private final UrlService urlService;

    @Autowired
    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping()
    public void addURLInfo(@Valid @RequestBody UrlDtoAdd urlInfo) throws IOException {
        urlService.addUrlInfo(urlInfo);
    }

    @GetMapping
    public List<UrlDtoGet> getUrls() {
        return urlService.getUrls();
    }

    @GetMapping("/{userId}")
    public UrlDtoGet getUrl(@PathVariable("userId") Long userId) {
        return urlService.getUrl(userId);
    }

    @DeleteMapping("/{userId}")
    public void deleteUrl(@PathVariable("userId") Long userId) {
        urlService.deleteUrl(userId);
    }

    @DeleteMapping("/all")
    public void deleteAll() {
        urlService.deleteAll();
    }

    @PutMapping("/{userId}")
    public void updateDisplayName(
            @PathVariable("userId") Long userId,
            @RequestParam(required = true) String newDisplayName) {
        urlService.updateDisplayName(userId, newDisplayName);
    }

}
