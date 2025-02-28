package com.springboot.healthcheck.dtos;

import com.springboot.healthcheck.models.Role;
import com.springboot.healthcheck.models.User;

public class UserGetDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private Role role;

    public UserGetDto(Long id, String firstName, String lastName, String username, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRoles(Role role) {
        this.role = role;
    }

    public static UserGetDto toDto(User user) {
        return new UserGetDto(user.getId(), user.getFirstName(), user.getLastName(),
                user.getUsername(), user.getRole());
    }

}
