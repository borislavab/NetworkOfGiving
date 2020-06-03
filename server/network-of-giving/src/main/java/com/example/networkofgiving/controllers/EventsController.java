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

    /**
     * Get the events associated with the requesting user
     *
     * @return
     * 200 OK status code on success along with a list of events
     * 403 FORBIDDEN status code if user is not authenticated
     * 404 NOT_FOUND status code on invalid charity id.
     * 400 BAD_REQUEST status code if the donated amount is greater than
     * the amount the charity remains to collect.
     */
    @GetMapping
    public List<EventDTO> getEvents() {

        return this.eventService.getUserEvents()
                .stream()
                .map(EventDTO::new)
                .collect(Collectors.toList());
    }
}
