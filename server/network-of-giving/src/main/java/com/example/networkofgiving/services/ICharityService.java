package com.example.networkofgiving.services;

import com.example.networkofgiving.entities.Charity;
import com.example.networkofgiving.models.CharityCreationDTO;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;
import java.util.NoSuchElementException;

public interface ICharityService {

    void createCharity(CharityCreationDTO charityCreationDTO) throws IllegalArgumentException;

    List<Charity> getAllCharities();

    Charity getCharityById(Long id) throws NoSuchElementException;

    void deleteCharityById(Long id) throws AccessDeniedException;

    void updateCharity(Charity charity);
}
