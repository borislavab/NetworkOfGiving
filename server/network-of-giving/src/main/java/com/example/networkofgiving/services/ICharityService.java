package com.example.networkofgiving.services;

import com.example.networkofgiving.entities.Charity;
import com.example.networkofgiving.entities.User;
import com.example.networkofgiving.models.CharityCreationDTO;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

public interface ICharityService {

    /**
     * Create new charity
     *
     * @param charityCreationDTO Contains the information needed to register a charity
     *
     * @throws IllegalArgumentException if both the requested amount and volunteer count are zero
     */
    void createCharity(CharityCreationDTO charityCreationDTO) throws IllegalArgumentException;

    /**
     * Get information about existing charities
     *
     * @param titleFilter An optional parameter for filtering only charities
     *                    with titles that contain the titleFilter string.

     * @return List of filtered charities
     */
    List<Charity> getAllCharities(Optional<String> titleFilter);

    /**
     * Get details about an existing charity
     *
     * @param id The id of the charity

     * @return The charity with the specified id
     * @throws NoSuchElementException if id is invalid
     */
    Charity getCharityById(Long id) throws NoSuchElementException;

    /**
     * Delete a charity
     *
     * @param id The id of the charity to delete
     *
     * @throws AccessDeniedException if the current user is not the owner of the charity
     */
    void deleteCharityById(Long id) throws AccessDeniedException;

    /**
     * Update an existing charity if it exists
     *
     * @param charity The charity to update
     */
    void updateCharity(Charity charity);

    /**
     * Edit a charity
     *
     * @param id The id of the charity to edit
     * @param charityCreationDTO Contains the updated charity information
     *
     * @throws NoSuchElementException if the id is invalid
     * @throws AccessDeniedException if the current user if not the owner of the charity
     */
    void editCharity(Long id, CharityCreationDTO charityCreationDTO)
            throws NoSuchElementException, AccessDeniedException;

    /**
     * Get users which are either volunteers or donators in the charity
     *
     * @param id The id of the charity
     * @return the set of participant for the charity
     */
    Set<User> getCharityParticipants(Long id);
}
