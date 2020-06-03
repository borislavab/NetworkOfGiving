package com.example.networkofgiving.services;

import com.example.networkofgiving.entities.Volunteering;
import com.example.networkofgiving.entities.VolunteeringKey;

import java.util.NoSuchElementException;

public interface IVolunteeringService {

    /**
     * Makes the current user a volunteer in the specified charity
     * @param charityId the id of the charity
     * @throws NoSuchElementException if a charity with such id does not exist
     * @throws IllegalArgumentException if the current user is already a volunteer in the charity or
     * the charity is not looking for more volunteers
     */
    void volunteerToCharity(Long charityId) throws NoSuchElementException, IllegalArgumentException;

    /**
     * Get the object representing the current user volunteering in the specified charity
     * @param charityId the id of the charity
     * @return information about the user volunteering
     * @throws NoSuchElementException if the user is not a volunteer in a charity with such id
     */
    Volunteering getUserVolunteering(Long charityId) throws NoSuchElementException;

    /**
     * Deletes the volunteering.
     * @param volunteeringId the volunteering id
     */
    void unvolunteer(VolunteeringKey volunteeringId);
}
