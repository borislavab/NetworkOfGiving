package com.example.networkofgiving.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {

    @GetMapping("/")
    public String hi() {
        return "Hello Spring!";
    }
}
