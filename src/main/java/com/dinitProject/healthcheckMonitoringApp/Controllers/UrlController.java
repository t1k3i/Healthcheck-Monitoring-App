package com.dinitProject.healthcheckMonitoringApp.Controllers;

import com.dinitProject.healthcheckMonitoringApp.Models.URLInfo;
import com.dinitProject.healthcheckMonitoringApp.Services.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<URLInfo> getUrls() {
        return urlService.getUrls();
    }

}
