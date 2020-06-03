package com.example.networkofgiving.models;

import com.example.networkofgiving.entities.Charity;

import java.math.BigDecimal;
import java.util.Date;

public class CharityResponseDTO extends CharityBasicResponseDTO {

    private String description;

    private BigDecimal amountRequired;

    private BigDecimal amountCollected;

    private Integer volunteersRequired;

    private Integer volunteersApplied;

    private Date createdAt;

    private String thumbnail;

    public CharityResponseDTO(Charity charity) {
        super(charity);
        this.description = charity.getDescription();
        this.amountCollected = charity.getAmountCollected();
        this.amountRequired = charity.getAmountRequired();
        this.volunteersApplied = charity.getVolunteersApplied();
        this.volunteersRequired = charity.getVolunteersRequired();
        this.createdAt = charity.getCreatedAt();
        this.thumbnail = charity.getThumbnail();
    }

    public CharityResponseDTO() {
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
