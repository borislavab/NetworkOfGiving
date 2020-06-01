package com.example.networkofgiving.services;

import com.example.networkofgiving.entities.Volunteering;

import java.util.NoSuchElementException;

public interface IVolunteeringService {

    void volunteerToCharity(Long charityId) throws NoSuchElementException, IllegalArgumentException;

    Volunteering getUserVolunteering(Long charityId);
}
