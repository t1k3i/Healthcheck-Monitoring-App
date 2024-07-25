package com.dinit.healthcheck.controllers;

import com.dinit.healthcheck.dtos.UserAddDto;
import com.dinit.healthcheck.dtos.UserGetDto;
import com.dinit.healthcheck.dtos.UserRegisterDto;
import com.dinit.healthcheck.services.JpaUserDetailsService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "users")
public class UserController {

    private final JpaUserDetailsService userDetailsService;

    public UserController(JpaUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping()
    public List<UserGetDto> getUsers(@RequestParam String currentUser) {
        return this.userDetailsService.getUsers(currentUser);
    }

    @GetMapping("/exists/{username}")
    public boolean usernameExists(@PathVariable("username") String username) {
        return userDetailsService.usernameExists(username);
    }

    @PostMapping("/authenticate")
    public UserGetDto authenticateUser(@Valid @RequestBody UserAddDto userAddDto) {
        return userDetailsService.authenticateUser(userAddDto);
    }

    @PostMapping("/register")
    public void registerUser(@Valid @RequestBody UserRegisterDto userRegisterDto) {
        this.userDetailsService.registerUser(userRegisterDto);
    }

    @DeleteMapping("/{urlId}")
    public void deleteUser(@PathVariable("urlId") Long urlId) {
        this.userDetailsService.deleteUser(urlId);
    }
}
