package com.example.networkofgiving.services;

import com.example.networkofgiving.entities.Charity;
import com.example.networkofgiving.entities.Event;
import com.example.networkofgiving.entities.User;
import com.example.networkofgiving.models.EventType;

import java.util.List;

public interface IEventService {

    void addUserEvent(User user, Charity charity, EventType eventType, String description);

    void addCharityEvent(Charity charity, EventType eventType, String description);

    List<Event> getUserEvents();
}
