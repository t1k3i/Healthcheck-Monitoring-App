package com.springboot.healthcheck.controllers;

import com.springboot.healthcheck.dtos.EmailAddDto;
import com.springboot.healthcheck.dtos.EmailDto;
import com.springboot.healthcheck.services.UrlService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/email/{urlId}/emails")
public class AlertMailController {

    private final UrlService urlService;

    public AlertMailController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping
    public List<EmailDto> getEmails(@PathVariable("urlId") Long urlId) {
        return urlService.getEmails(urlId);
    }

    @DeleteMapping("/{emailId}")
    public void deleteEmail(@PathVariable("urlId") Long urlId, @PathVariable("emailId") Long emailId) {
        urlService.deleteEmail(urlId, emailId);
    }

    @PutMapping
    public void addEmailToUrlInfo(@PathVariable("urlId") Long urlId, @Valid @RequestBody EmailAddDto emailAddDto) {
        urlService.addEmailToUrlInfo(urlId, emailAddDto.getEmail());
    }
}

