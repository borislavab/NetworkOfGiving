package com.example.networkofgiving.entities;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "donations")
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    @JoinColumn(name = "donator_id")
    User donator;

    @ManyToOne
    @JoinColumn(name = "charity_id")
    Charity charity;

    @Column(updatable = false)
    private Instant timestamp;

    public Donation() {
    }

    public Donation(User donator, Charity charity) {
        this.donator = donator;
        this.charity = charity;
        this.timestamp = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getDonator() {
        return donator;
    }

    public void setDonator(User donator) {
        this.donator = donator;
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
