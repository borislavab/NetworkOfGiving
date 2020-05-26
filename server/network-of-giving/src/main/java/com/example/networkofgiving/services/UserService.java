package com.example.networkofgiving.services;

import com.example.networkofgiving.entities.User;
import com.example.networkofgiving.models.RegistrationDTO;
import com.example.networkofgiving.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public void register(RegistrationDTO registrationDTO) {
        validateRegistrationDTO(registrationDTO);
        String encodedPassword = passwordEncoder.encode(registrationDTO.getPassword());
        registrationDTO.setPassword(encodedPassword);
        User newUser = constructUserFromRegistrationDTO(registrationDTO);
        this.userRepository.saveAndFlush(newUser);
    }

    private User constructUserFromRegistrationDTO(RegistrationDTO registrationDTO) {
        User user = new User(null,
                registrationDTO.getUsername(),
                registrationDTO.getPassword(),
                registrationDTO.getName(),
                registrationDTO.getAge(),
                registrationDTO.getGender(),
                registrationDTO.getLocation()
        );
        return user;
    }

    private void validateRegistrationDTO(RegistrationDTO registrationDTO) {
        if (!isValidUsername(registrationDTO.getUsername())) {
            throw new IllegalArgumentException("Username invalid!");
        }
        if (!isValidPassword(registrationDTO.getPassword())) {
            throw new IllegalArgumentException("Password invalid!");
        }
        if (!isValidName(registrationDTO.getName())) {
            throw new IllegalArgumentException("Name invalid!");
        }
        if (!isValidAge(registrationDTO.getAge())) {
            throw new IllegalArgumentException("Age invalid!");
        }
        if (!isValidLocation(registrationDTO.getLocation())) {
            throw new IllegalArgumentException("Location invalid!");
        }
    }

    private boolean isValidUsername(String username) {
        if (username == null) {
            return false;
        }
        String regex = "^[aA-zZ]\\w{5,29}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(username);
        return m.matches();
    }

    private boolean isValidPassword(String password) {
        if (password == null || password.length() < 6) {
            return false;
        }
        return true;
    }

    private boolean isValidName(String name) {
        if (name == null) {
            return false;
        }

        String regex = "^[aA-zZ][aA-zZ ]*$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(name);
        return m.matches();
    }

    private boolean isValidAge(Integer age) {
        return age != null && age > 0;
    }

    private boolean isValidLocation(String location) {
        if (location == null) {
            return true;
        }

        String regex = "^[aA-zZ][aA-zZ ]*$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(location);
        return m.matches();
    }
}
