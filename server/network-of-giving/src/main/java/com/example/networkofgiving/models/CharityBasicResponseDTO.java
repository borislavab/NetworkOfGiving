package com.example.networkofgiving.models;

import com.example.networkofgiving.entities.Charity;

import java.io.Serializable;

public class CharityBasicResponseDTO implements Serializable {
    private Long id;
    private String title;

    public CharityBasicResponseDTO(Charity charity) {
        this.id = charity.getId();
        this.title = charity.getTitle();
    }

    public CharityBasicResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
