package com.example.networkofgiving.entities;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "volunteerings")
public class Volunteering {

    @EmbeddedId
    private VolunteeringKey id;

    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @ManyToOne
    @MapsId("charity_id")
    @JoinColumn(name = "charity_id")
    private Charity charity;

    @Column(name = "charity_id", insertable = false, updatable = false)
    private Long charityId;

    @Column(updatable = false)
    private Instant timestamp;

    public Volunteering() {
    }

    public Volunteering(User user, Charity charity) {
        this.id = new VolunteeringKey(user.getId(), charity.getId());
        this.user = user;
        this.charity = charity;
        this.timestamp = Instant.now();
    }

    public VolunteeringKey getId() {
        return id;
    }

    public void setId(VolunteeringKey id) {
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

    public Long getUserId() {
        return userId;
    }

    public Long getCharityId() {
        return charityId;
    }
}
