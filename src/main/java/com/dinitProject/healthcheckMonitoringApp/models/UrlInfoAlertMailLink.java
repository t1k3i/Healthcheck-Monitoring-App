package com.dinitProject.healthcheckMonitoringApp.models;

import jakarta.persistence.*;

@Entity
public class UrlInfoAlertMailLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "urlinfo_id", nullable = false)
    private URLInfo urlInfo;
    @ManyToOne
    @JoinColumn(name = "alertmail_id", nullable = false)
    private AlertMail alertMail;

    public UrlInfoAlertMailLink() {}

    public UrlInfoAlertMailLink(Long id, URLInfo urlInfo, AlertMail alertMail) {
        this.id = id;
        this.urlInfo = urlInfo;
        this.alertMail = alertMail;
    }

    public Long getId() {
        return id;
    }

    public URLInfo getUrlInfo() {
        return urlInfo;
    }

    public AlertMail getAlertMail() {
        return alertMail;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUrlInfo(URLInfo urlInfo) {
        this.urlInfo = urlInfo;
    }

    public void setAlertMail(AlertMail alertMail) {
        this.alertMail = alertMail;
    }

    @Override
    public String toString() {
        return "UrlInfoAlertMailLink{" +
                "id=" + id +
                ", urlInfo=" + urlInfo +
                ", alertMail=" + alertMail +
                '}';
    }
}
