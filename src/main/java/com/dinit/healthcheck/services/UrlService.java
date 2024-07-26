package com.dinit.healthcheck.services;

import com.dinit.healthcheck.dtos.*;
import com.dinit.healthcheck.exceptions.conflict.DisplayNameConflictException;
import com.dinit.healthcheck.exceptions.conflict.EmailConflictException;
import com.dinit.healthcheck.exceptions.conflict.UrlConflictException;
import com.dinit.healthcheck.exceptions.notfound.EmailNotFoundException;
import com.dinit.healthcheck.exceptions.notfound.UrlNotFoundException;
import com.dinit.healthcheck.models.AlertMail;
import com.dinit.healthcheck.models.URLInfo;
import com.dinit.healthcheck.repositories.AlertMailRepository;
import com.dinit.healthcheck.repositories.UrlRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private final AlertMailRepository alertMailRepository;

    public UrlService(UrlRepository urlRepository, AlertMailRepository alertMailRepository) {
        this.urlRepository = urlRepository;
        this.alertMailRepository = alertMailRepository;
    }

    public List<UrlGetDto> getUrls(boolean healthyFirst) {
        List<UrlGetDto> list = new ArrayList<>();
        List<URLInfo> urls = healthyFirst ? urlRepository.findAllOrderByHealthyDescThenById()
                : urlRepository.findAllOrderByHealthyAscThenById();
        for (URLInfo urlInfo : urls)
            list.add(UrlGetDto.toUrlDto(urlInfo));
        return list;
    }

    public UrlGetDto getUrl(Long id) {
        URLInfo urlInfo = urlRepository.findById(id).orElse(null);
        if (urlInfo == null)
            throw new UrlNotFoundException();
        return UrlGetDto.toUrlDto(urlInfo);
    }

    public List<UrlGetDto> searchUrls(String query) {
        return this.urlRepository.findByUrlContainingOrDisplayNameContaining(query, query)
                .stream()
                .map(UrlGetDto::toUrlDto)
                .toList();
    }

    public void deleteUrl(Long urlId) {
        if (!urlRepository.existsById(urlId))
            throw new UrlNotFoundException();
        urlRepository.deleteById(urlId);
    }

    public void deleteAll() {
        urlRepository.deleteAll();
    }

    public void addUrlInfo(UrlAddDto url) {
        if (urlExists(url.getUrl()))
            throw new UrlConflictException();
        if (displayNameExists(url.getDisplayName()))
            throw new DisplayNameConflictException();
        var urlInfo = UrlAddDto.toEntity(url);
        urlRepository.save(urlInfo);
    }

    private boolean displayNameExists(String displayName) {
        return urlRepository.findByDisplayName(displayName).isPresent();
    }

    private boolean urlExists(String url) {
        return urlRepository.findByUrl(url).isPresent();
    }

    @Transactional
    public void updateDisplayName(Long urlId, UrlUpdateDto newUrlInfo) {
        URLInfo urlInfo = urlRepository.findById(urlId).orElse(null);
        if (urlInfo == null)
            throw new UrlNotFoundException();
        String newDisplayName = newUrlInfo.getDisplayName();
        String newUrl = newUrlInfo.getUrl();
        Integer newFrequency = newUrlInfo.getFrequency();
        if (newDisplayName != null && displayNameExists(newDisplayName) && !newDisplayName.equals(urlInfo.getDisplayName()))
            throw new DisplayNameConflictException();
        if (newUrl != null && urlExists(newUrl) && !newUrl.equals(urlInfo.getUrl()))
            throw new UrlConflictException();
        if (newDisplayName != null)
            urlInfo.setDisplayName(newDisplayName);
        if (newUrl != null)
            urlInfo.setUrl(newUrl);
        if (newFrequency != null)
            urlInfo.setFrequency(newFrequency);
    }

    @Transactional
    public void toggleMute(Long urlId) {
        URLInfo urlInfo = this.urlRepository.findById(urlId).orElse(null);
        if (urlInfo == null)
            throw new UrlNotFoundException();
        urlInfo.setMute(!urlInfo.isMute());
    }

    @Transactional
    public void addEmailToUrlInfo(Long urlId, String email) {
        URLInfo urlInfo = urlRepository.findByIdWithAlertMails(urlId).orElse(null);
        if (urlInfo == null)
            throw new UrlNotFoundException();
        AlertMail alertMail = alertMailRepository.findByMail(email).orElse(new AlertMail(email));
        if (emailExists(alertMail, urlInfo))
            throw new EmailConflictException();
        urlInfo.getAlertMails().add(alertMail);
    }

    public List<EmailDto> getEmails(Long urlId) {
        if (!urlRepository.existsById(urlId))
            throw new UrlNotFoundException();
        List<AlertMail> alertMails = urlRepository.findAlertMailsByUrlId(urlId);
        List<EmailDto> emailDtos = new ArrayList<>();
        for (AlertMail alertMail : alertMails) {
            emailDtos.add(new EmailDto(alertMail.getId(), alertMail.getMail()));
        }
        return emailDtos;
    }

    private boolean emailExists(AlertMail alertMail, URLInfo urlInfo) {
        Set<AlertMail> conn = urlInfo.getAlertMails();
        String name = alertMail.getMail();
        for (AlertMail mail : conn) {
            if (mail.getMail().equals(name))
                return true;
        }
        return false;
    }

    @Transactional
    public void deleteEmail(Long urlId, Long emailId) {
        URLInfo urlInfo = urlRepository.findById(urlId).orElse(null);
        if (urlInfo == null)
            throw new UrlNotFoundException();
        AlertMail alertMail = alertMailRepository.findById(emailId).orElse(null);
        if (alertMail == null)
            throw new EmailNotFoundException();
        if (!urlInfo.getAlertMails().contains(alertMail))
            throw new EmailNotFoundException();
        urlInfo.getAlertMails().remove(alertMail);
        alertMail.getUrlInfos().remove(urlInfo);
    }
}
