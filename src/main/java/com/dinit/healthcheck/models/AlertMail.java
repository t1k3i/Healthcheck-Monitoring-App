package com.dinit.healthcheck.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "alertmail")
public class AlertMail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mail;

    @ManyToMany(mappedBy = "alertMails", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JsonBackReference
    private Set<URLInfo> urlInfos;

    public AlertMail() {}

    public AlertMail(String mail) {
        this.mail = mail;
    }

    public Long getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }

    public Set<URLInfo> getUrlInfos() {
        return urlInfos;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setUrlInfos(Set<URLInfo> urlInfos) {
        this.urlInfos = urlInfos;
    }

    @Override
    public String toString() {
        return "AlertMail{" +
                "id=" + id +
                ", mail='" + mail + '\'' +
                ", urlInfos=" + urlInfos +
                '}';
    }
}
