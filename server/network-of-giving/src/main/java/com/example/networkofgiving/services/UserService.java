package com.example.networkofgiving.services;

import com.example.networkofgiving.entities.User;
import com.example.networkofgiving.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService implements IUserService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void register(User user) {
        if (!isValidUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username invalid!");
        }
        String password = user.getPassword();
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("Password invalid!");
        }
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        this.userRepository.saveAndFlush(user);
    }

    private boolean isValidUsername(String username) {
        String regex = "^[aA-zZ]\\w{5,29}$";
        Pattern p = Pattern.compile(regex);
        if (username == null) {
            return false;
        }
        Matcher m = p.matcher(username);
        return m.matches();
    }

    private boolean isValidPassword(String password) {
        if (password == null || password.length() < 6) {
            return false;
        }
        return true;
    }
}
