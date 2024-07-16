package com.dinit.healthcheck.dtos;

public class UserAddDto {
    private String username;
    private String password;

    public UserAddDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return "UserAddDto [username=" + username + ", password=" + password + "]";
    }
}
