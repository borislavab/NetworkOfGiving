package com.example.networkofgiving.services;

import com.example.networkofgiving.entities.User;
import com.example.networkofgiving.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;

@Service
public class UserService implements IUserService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findById(username).orElse(null);
    }

    @Override
    public void register(User user) {
        if (this.userRepository.existsById(user.getUsername())) {
            throw new EntityExistsException("Username already exists!");
        }
        String password = user.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        this.userRepository.saveAndFlush(user);
    }
}
