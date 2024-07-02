package com.dinitProject.healthcheckMonitoringApp.Controllers;

import com.dinitProject.healthcheckMonitoringApp.Models.URLInfo;
import com.dinitProject.healthcheckMonitoringApp.Services.UrlService;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "url")
public class UrlController {

    private final UrlService urlService;

    @Autowired
    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/add")
    public void addURLInfo(@RequestBody URLInfo urlInfo) {
        urlService.addUrlInfo(urlInfo);
    }

    @GetMapping
    public List<URLInfo> getUrls() {
        return urlService.getUrls();
    }

    @GetMapping("/{userId}")
    public URLInfo getUrl(@PathVariable Long userId) {
        return urlService.getUrl(userId);
    }

}
