package com.example.networkofgiving.entities;

import com.example.networkofgiving.models.EventType;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "charity_id")
    private Charity charity;

    @Column(nullable = false)
    private Instant timestamp;

    @Column(nullable = false)
    private EventType type;

    @Column(nullable = false)
    private String description;

    public Event() {
    }

    public Event(User user, Charity charity, EventType eventType, String description) {
        this.user = user;
        this.charity = charity;
        this.timestamp = Instant.now();
        this.type = eventType;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Charity getCharity() {
        return charity;
    }

    public void setCharity(Charity charity) {
        this.charity = charity;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
