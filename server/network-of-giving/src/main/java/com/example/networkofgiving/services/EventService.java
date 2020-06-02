package com.example.networkofgiving.services;

import com.example.networkofgiving.entities.Charity;
import com.example.networkofgiving.entities.Event;
import com.example.networkofgiving.entities.User;
import com.example.networkofgiving.models.EventType;
import com.example.networkofgiving.repositories.IEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService implements IEventService {

    @Autowired
    private IEventRepository eventRepository;

    @Autowired
    private IUserService userService;

    @Override
    public void addEvent(User user, Charity charity, EventType eventType, String description) {
        Event event = new Event(user, charity, eventType, description);
        this.eventRepository.save(event);
    }

    @Override
    public List<Event> getEvents() {
        User currentUser = this.userService.getCurrentlyAuthenticatedUser();
        return this.eventRepository.findAllByUserId(currentUser.getId(), sortByDateDesc());
    }

    private Sort sortByDateDesc() {
        return Sort.by(new Sort.Order(Sort.Direction.DESC, "timestamp"));
    }

}
