package com.dinitProject.healthcheckMonitoringApp.models;

import jakarta.persistence.*;

@Entity
@Table(name = "alertmail")
public class AlertMail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mail;

    public AlertMail() {}

    public AlertMail(Long id, String mail) {
        this.id = id;
        this.mail = mail;
    }

    public Long getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "AlertMail{" +
                "id=" + id +
                ", mail='" + mail + '\'' +
                '}';
    }

}
