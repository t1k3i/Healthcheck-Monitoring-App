package com.dinitProject.healthcheckMonitoringApp.controllers;

import com.dinitProject.healthcheckMonitoringApp.dtos.UrlDtoAdd;
import com.dinitProject.healthcheckMonitoringApp.dtos.UrlDtoGet;
import com.dinitProject.healthcheckMonitoringApp.services.UrlService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @GetMapping
    public ResponseEntity<List<UrlDtoGet>> getUrls() {
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(urlService.getUrls());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UrlDtoGet> getUrl(@PathVariable("userId") Long userId) {
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(urlService.getUrl(userId));
    }

    @PostMapping
    public void addURLInfo(@Valid @RequestBody UrlDtoAdd urlInfo) throws IOException {
        urlService.addUrlInfo(urlInfo);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUrl(@PathVariable("userId") Long userId) {
        urlService.deleteUrl(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body("");
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteAll() {
        urlService.deleteAll();
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body("");
    }

    @PutMapping("/{userId}")
    public void updateDisplayName(
            @PathVariable("userId") Long userId,
            @RequestParam(required = true) String newDisplayName) {
        urlService.updateDisplayName(userId, newDisplayName);
    }

}