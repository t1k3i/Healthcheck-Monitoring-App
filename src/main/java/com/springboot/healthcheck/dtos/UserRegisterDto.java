package com.springboot.healthcheck.dtos;

import com.springboot.healthcheck.models.Role;
import com.springboot.healthcheck.models.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserRegisterDto {

    @NotNull(message = "Name can not be null")
    @NotEmpty(message = "Last name can not be empty")
    private String firstName;
    @NotNull(message = "Url can not be null")
    @NotEmpty(message = "Url can not be empty")
    private String lastName;
    @Size(min = 3, max = 255, message = "Length should be between 3 and 255")
    private String username;
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$",
            message = "Invalid password"
    )
    private String password;
    private String role;

    public UserRegisterDto(String firstName, String lastName, String username, String password, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static User toUser(UserRegisterDto userRegisterDto, Role role) {
        return new User(userRegisterDto.getFirstName(), userRegisterDto.getLastName(),
                userRegisterDto.getUsername(), userRegisterDto.getPassword(), role);
    }

}
