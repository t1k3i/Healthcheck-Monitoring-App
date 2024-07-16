package com.dinit.healthcheck.controllers;

import com.dinit.healthcheck.dtos.UserAddDto;
import com.dinit.healthcheck.dtos.UserGetDto;
import com.dinit.healthcheck.services.JpaUserDetailsService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "users")
public class UserController {

    private final JpaUserDetailsService userDetailsService;

    public UserController(JpaUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/authenticate")
    public UserGetDto authenticateUser(@Valid @RequestBody UserAddDto userAddDto) {
        return userDetailsService.authenticateUser(userAddDto);
    }
}
