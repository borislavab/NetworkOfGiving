package com.example.networkofgiving.models;

import com.example.networkofgiving.entities.Donation;

import java.math.BigDecimal;
import java.time.Instant;

public class DonationDTO {
    private Long id;
    private Long donatorId;
    private Long charityId;
    private BigDecimal amount;
    private Instant timestamp;

    public DonationDTO(Donation donation) {
        this.id = donation.getId();
        this.donatorId = donation.getDonatorId();
        this.charityId = donation.getCharityId();
        this.amount = donation.getDonationAmount();
        this.timestamp = donation.getTimestamp();
    }

    public Long getId() {
        return id;
    }

    public Long getDonatorId() {
        return donatorId;
    }

    public Long getCharityId() {
        return charityId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
