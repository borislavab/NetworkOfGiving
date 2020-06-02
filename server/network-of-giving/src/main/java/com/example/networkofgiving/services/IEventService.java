package com.example.networkofgiving.services;

import com.example.networkofgiving.entities.Charity;
import com.example.networkofgiving.entities.User;
import com.example.networkofgiving.models.EventType;

public interface IEventService {

    void addEvent(User user, Charity charity, EventType eventType, String description);
}
