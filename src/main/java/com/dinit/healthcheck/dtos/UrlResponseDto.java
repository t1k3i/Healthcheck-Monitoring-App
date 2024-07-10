package com.dinit.healthcheck.dtos;

import org.json.JSONObject;

import java.util.Map;

public class UrlResponseDto {
    private String status;
    private String totalDuration;
    private Map<String, Object> entries;

    public UrlResponseDto(String status, String totalDuration, Map<String, Object> entries) {
        this.status = status;
        this.totalDuration = totalDuration;
        this.entries = entries;
    }

    public String getStatus() {
        return status;
    }

    public String getTotalDuration() {
        return totalDuration;
    }

    public Map<String, Object> getEntries() {
        return entries;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTotalDuration(String totalDuration) {
        this.totalDuration = totalDuration;
    }

    public void setEntries(Map<String, Object> entries) {
        this.entries = entries;
    }

    @Override
    public String toString() {
        return "UrlResponseDto{" +
                "status=" + status +
                ", totalDuration='" + totalDuration + '\'' +
                ", entries=" + entries +
                '}';
    }

    public static UrlResponseDto fromJson(JSONObject jsonObject) {
        String status = jsonObject.getString("status");
        String totalDuration = jsonObject.getString("totalDuration");
        Map<String, Object> entries = jsonObject.getJSONObject("entries").toMap();
        return new UrlResponseDto(status, totalDuration, entries);
    }
}
