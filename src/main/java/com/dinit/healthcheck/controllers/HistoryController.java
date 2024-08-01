package com.dinit.healthcheck.controllers;

import com.dinit.healthcheck.dtos.HistoryDto;
import com.dinit.healthcheck.services.UrlService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/history/{urlId}")
public class HistoryController {

    private final UrlService urlService;

    public HistoryController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping
    public List<HistoryDto> getEmails(@PathVariable("urlId") Long urlId) {
        return urlService.getHistory(urlId);
    }

    @DeleteMapping
    public void deleteHistory(@PathVariable("urlId") Long urlId) {
        urlService.deleteHistory(urlId);
    }

}
