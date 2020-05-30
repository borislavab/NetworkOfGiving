package com.example.networkofgiving.services;

import org.springframework.security.access.AccessDeniedException;

import java.util.NoSuchElementException;

public interface IVolunteeringService {

    void volunteerToCharity(Long charityId) throws NoSuchElementException, AccessDeniedException;
}
