package com.example.networkofgiving.services;

import com.example.networkofgiving.entities.User;
import com.example.networkofgiving.models.RegistrationDTO;
import com.example.networkofgiving.repositories.IUserRepository;
import com.example.networkofgiving.security.AuthenticatedUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        String encodedPassword = passwordEncoder.encode(registrationDTO.getPassword());
        registrationDTO.setPassword(encodedPassword);
        User newUser = constructUserFromRegistrationDTO(registrationDTO);
        this.userRepository.saveAndFlush(newUser);
    }

    public User getCurrentlyAuthenticatedUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        AuthenticatedUserInfo principal = (AuthenticatedUserInfo) securityContext.getAuthentication().getPrincipal();
        return principal.getUser();
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
}
