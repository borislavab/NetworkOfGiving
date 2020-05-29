package com.example.networkofgiving.models;

import com.example.networkofgiving.entities.Charity;

import java.math.BigDecimal;
import java.util.Date;

public class CharityBasicResponseDTO {
    private Long id;

    private String title;

    private String description;

    private BigDecimal amountRequired;

    private BigDecimal amountCollected;

    private Integer volunteersRequired;

    private Integer volunteersApplied;

    private Date createdAt;

    private String thumbnail;

    public CharityBasicResponseDTO(Charity charity) {
        this.id = charity.getId();
        this.title = charity.getTitle();
        this.description = charity.getDescription();
        this.amountCollected = charity.getAmountCollected();
        this.amountRequired = charity.getAmountRequired();
        this.volunteersApplied = charity.getVolunteersApplied();
        this.volunteersRequired = charity.getVolunteersRequired();
        this.createdAt = charity.getCreatedAt();
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

    public Date getCreatedAt() {
        return createdAt;
    }
}
