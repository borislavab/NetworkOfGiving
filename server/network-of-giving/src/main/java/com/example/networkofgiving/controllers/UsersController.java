package com.example.networkofgiving.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {

    @PostMapping("/login")
    public String login() {
        return "Logged in!";
    }

    @PostMapping("/register")
    public String register() {
        return "Registered!";
    }

    @GetMapping("/authenticated")
    public String authenticated() {
        return "Authenticated!";
    }
}
