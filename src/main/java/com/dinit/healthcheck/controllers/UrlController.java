package com.dinit.healthcheck.controllers;

import com.dinit.healthcheck.dtos.*;
import com.dinit.healthcheck.models.URLInfo;
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
    public List<UrlGetDto> getUrls() {
        return urlService.getUrls();
    }

    @GetMapping("/{urlId}")
    public UrlGetDto getUrl(@PathVariable("urlId") Long urlId) {
        return urlService.getUrl(urlId);
    }

    @GetMapping("/search")
    public List<UrlGetDto> searchUrls(@RequestParam String query) {
        return urlService.searchUrls(query);
    }

    @DeleteMapping("/{urlId}")
    public void deleteUrl(@PathVariable("urlId") Long urlId) {
        urlService.deleteUrl(urlId);
    }

    @DeleteMapping()
    public void deleteAll() {
        urlService.deleteAll();
    }

    @PostMapping
    public void addURLInfo(@Valid @RequestBody UrlAddDto urlInfo) {
        urlService.addUrlInfo(urlInfo);
    }

    @PutMapping("/{urlId}")
    public void updateDisplayName(
            @PathVariable("urlId") Long urlId,
            @Valid @RequestBody UrlUpdateDto urlInfo) {
        urlService.updateDisplayName(urlId, urlInfo);
    }

}