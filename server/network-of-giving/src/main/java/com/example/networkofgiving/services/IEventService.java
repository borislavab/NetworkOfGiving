package com.example.networkofgiving.services;

import com.example.networkofgiving.entities.Charity;
import com.example.networkofgiving.entities.Event;
import com.example.networkofgiving.entities.User;
import com.example.networkofgiving.models.EventType;

import java.util.List;

public interface IEventService {

    /**
     * Add an event associated with a single user
     *
     * @param user the user to be notified of the event
     * @param charity the charity to be associated with the event
     * @param eventType the type of the event
     * @param description the event description
     */
    void addUserEvent(User user, Charity charity, EventType eventType, String description);

    /**
     * Add an event associated with all users participating in the charity
     *
     * @param charity the charity to be associated with the event
     * @param eventType the type of the event
     * @param description the event description
     */
    void addCharityEvent(Charity charity, EventType eventType, String description);

    /**
     * Get all events associated with the current user
     *
     * @return a list of event information about the events associated with the current user
     */
    List<Event> getUserEvents();
}
