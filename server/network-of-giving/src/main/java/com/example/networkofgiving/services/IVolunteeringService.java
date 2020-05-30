package com.example.networkofgiving.services;

import com.example.networkofgiving.entities.Volunteering;
import org.springframework.security.access.AccessDeniedException;

import java.util.NoSuchElementException;

public interface IVolunteeringService {

    void volunteerToCharity(Long charityId) throws NoSuchElementException, AccessDeniedException;

    Volunteering getUserVolunteering(Long charityId);
}
