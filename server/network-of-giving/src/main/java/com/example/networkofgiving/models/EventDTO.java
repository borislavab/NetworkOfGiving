package com.example.networkofgiving.models;

import com.example.networkofgiving.entities.Event;

import java.io.Serializable;
import java.time.Instant;

public class EventDTO implements Serializable {
    private Long charityId;

    private Instant timestamp;

    private EventType type;

    private String description;

    public EventDTO(Event event) {
        this.charityId = event.getCharityId();
        this.description = event.getDescription();
        this.type = event.getType();
        this.timestamp = event.getTimestamp();
    }

    public Long getCharityId() {
        return charityId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public EventType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
