package com.example.networkofgiving.models;

import com.example.networkofgiving.entities.Charity;

public class CharityDetailsResponseDTO extends CharityBasicResponseDTO {

    private Long ownerId;

    private String ownerUsername;

    private String ownerName;

    public CharityDetailsResponseDTO(Charity charity) {
        super(charity);
        this.ownerId = charity.getOwnerId();
        this.ownerUsername = charity.getOwner().getUsername();
        this.ownerName = charity.getOwner().getName();
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public String getOwnerName() {
        return ownerName;
    }
}
