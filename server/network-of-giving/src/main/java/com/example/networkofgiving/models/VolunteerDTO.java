package com.example.networkofgiving.models;

import com.example.networkofgiving.entities.Volunteering;

import java.io.Serializable;
import java.time.Instant;

public class VolunteerDTO implements Serializable {
    private Long userId;
    private Long charityId;
    private String charityTitle;
    private Instant timestamp;

    public VolunteerDTO(Volunteering volunteering) {
        this.userId = volunteering.getId().getUserId();
        this.charityId = volunteering.getId().getCharityId();
        this.charityTitle = volunteering.getCharity().getTitle();
        this.timestamp = volunteering.getTimestamp();
    }

    public Long getUserId() {
        return userId;
    }

    public Long getCharityId() {
        return charityId;
    }

    public String getCharityTitle() {
        return charityTitle;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
