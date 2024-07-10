package com.dinit.healthcheck.controllers;

import com.dinit.healthcheck.dtos.UrlDtoAdd;
import com.dinit.healthcheck.dtos.UrlDtoGet;
import com.dinit.healthcheck.dtos.UrlUpdateDto;
import com.dinit.healthcheck.services.UrlService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "urls")
public class UrlController {

    private final UrlService urlService;

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