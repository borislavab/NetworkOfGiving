package com.example.networkofgiving.services;

import com.example.networkofgiving.entities.Donation;
import com.example.networkofgiving.entities.User;
import com.example.networkofgiving.models.RegistrationDTO;
import com.example.networkofgiving.models.UserInformationDTO;

import java.util.Set;

public interface IUserService {
    /**
     * Fetches user information by user's username
     * @param username the user's username
     * @return the user object
     */
    User getUserByUsername(String username);

    /**
     * Fetches user information by user's id
     * @param id the user id
     * @return the user object
     */
    User getUserById(Long id);

    /**
     * Registers user
     * @param registrationDTO the user data to be stored
     */
    void register(RegistrationDTO registrationDTO);

    /**
     * Fetches information from the security context about the currently authenticated user
     * @return the authenticated user object
     */
    User getCurrentlyAuthenticatedUser();

    /**
     * Fetches detailed information about the currently authenticated user
     * @return the user data
     */
    UserInformationDTO getCurrentUserInformation();

    /**
     * Fetches the current user's donations
     * @return the set of all donations (which have not been refunded) of the user
     */
    Set<Donation> getUserDonations();
}
