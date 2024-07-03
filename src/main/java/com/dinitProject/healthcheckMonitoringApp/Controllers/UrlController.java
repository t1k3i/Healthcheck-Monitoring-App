package com.dinitProject.healthcheckMonitoringApp.Controllers;

import com.dinitProject.healthcheckMonitoringApp.Dtos.UrlDtoAdd;
import com.dinitProject.healthcheckMonitoringApp.Dtos.UrlDtoGet;
import com.dinitProject.healthcheckMonitoringApp.Models.URLInfo;
import com.dinitProject.healthcheckMonitoringApp.Services.UrlService;
import jakarta.validation.Valid;
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
    public void addURLInfo(@Valid @RequestBody UrlDtoAdd urlInfo) {
        urlService.addUrlInfo(urlInfo);
    }

    @GetMapping
    public List<UrlDtoGet> getUrls() {
        return urlService.getUrls();
    }

    @GetMapping("/{userId}")
    public UrlDtoGet getUrl(@PathVariable Long userId) {
        return urlService.getUrl(userId);
    }

    @DeleteMapping("/delete/{userId}")
    public void deleteUrl(@PathVariable("userId") Long userId) {
        urlService.deleteUrl(userId);
    }

}
