package com.example.networkofgiving.services;

import com.example.networkofgiving.entities.Charity;
import com.example.networkofgiving.entities.Event;
import com.example.networkofgiving.entities.User;
import com.example.networkofgiving.models.EventType;
import com.example.networkofgiving.repositories.IEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService implements IEventService {

    @Autowired
    private IEventRepository eventRepository;

    @Override
    public void addEvent(User user, Charity charity, EventType eventType, String description) {
        Event event = new Event(user, charity, eventType, description);
        this.eventRepository.save(event);
    }
}
