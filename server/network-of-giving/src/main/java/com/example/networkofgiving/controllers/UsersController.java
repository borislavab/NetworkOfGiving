package com.example.networkofgiving.controllers;

import com.example.networkofgiving.entities.User;
import com.example.networkofgiving.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public String login() {
        return "Logged in!";
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void register(@RequestBody User user) {
        this.userService.register(user);
    }

    @GetMapping("/authenticated")
    public String authenticated() {
        return "Authenticated!";
    }

    @ResponseStatus(code = HttpStatus.CONFLICT, reason = "Username already exists!")
    @ExceptionHandler(DataIntegrityViolationException.class)
    public void conflict() { }
}
