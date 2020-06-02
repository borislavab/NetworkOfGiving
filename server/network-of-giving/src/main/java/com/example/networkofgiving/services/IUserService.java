package com.example.networkofgiving.services;

import com.example.networkofgiving.entities.User;
import com.example.networkofgiving.models.RegistrationDTO;
import com.example.networkofgiving.models.UserInformationDTO;

public interface IUserService {
    User getUserByUsername(String username);

    User getUserById(Long id);

    void register(RegistrationDTO registrationDTO);

    User getCurrentlyAuthenticatedUser();

    UserInformationDTO getCurrentUserInformation();
}
