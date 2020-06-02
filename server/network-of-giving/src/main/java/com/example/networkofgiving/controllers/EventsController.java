package com.example.networkofgiving.controllers;

import com.example.networkofgiving.models.EventDTO;
import com.example.networkofgiving.services.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events/me")
public class EventsController {

    @Autowired
    private IEventService eventService;

    @GetMapping
    public List<EventDTO> getEvents() {

        return this.eventService.getEvents()
                .stream()
                .map(EventDTO::new)
                .collect(Collectors.toList());
    }
}
