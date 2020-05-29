package com.example.networkofgiving.models;

import com.example.networkofgiving.entities.Charity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CharityResponseDTO implements Serializable {
    private Long id;

    private String title;

    private String description;

    private BigDecimal amountRequired;

    private BigDecimal amountCollected;

    private Integer volunteersRequired;

    private Integer volunteersApplied;

    private Date createdAt;

    private Long ownerId;

    private String thumbnail;

    public CharityResponseDTO(Charity charity) {
        this.id = charity.getId();
        this.title = charity.getTitle();
        this.description = charity.getDescription();
        this.amountCollected = charity.getAmountCollected();
        this.amountRequired = charity.getAmountRequired();
        this.volunteersApplied = charity.getVolunteersApplied();
        this.volunteersRequired = charity.getVolunteersRequired();
        this.createdAt = charity.getCreatedAt();
        this.ownerId = charity.getOwnerId();
        this.thumbnail = charity.getThumbnail();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmountRequired() {
        return amountRequired;
    }

    public BigDecimal getAmountCollected() {
        return amountCollected;
    }

    public Integer getVolunteersRequired() {
        return volunteersRequired;
    }

    public Integer getVolunteersApplied() {
        return volunteersApplied;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
