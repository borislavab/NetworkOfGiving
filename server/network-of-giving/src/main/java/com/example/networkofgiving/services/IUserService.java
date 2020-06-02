package com.example.networkofgiving.services;

import com.example.networkofgiving.entities.Donation;
import com.example.networkofgiving.entities.User;
import com.example.networkofgiving.models.RegistrationDTO;
import com.example.networkofgiving.models.UserInformationDTO;

import java.util.Set;

public interface IUserService {
    User getUserByUsername(String username);

    User getUserById(Long id);

    void register(RegistrationDTO registrationDTO);

    User getCurrentlyAuthenticatedUser();

    UserInformationDTO getCurrentUserInformation();

    Set<Donation> getUserDonations();
}
