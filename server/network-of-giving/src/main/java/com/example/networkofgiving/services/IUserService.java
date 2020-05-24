package com.example.networkofgiving.services;

import com.example.networkofgiving.entities.User;

public interface IUserService {
    User getUserByUsername(String username);

    User getUserById(Long id);

    void register(User user);
}
