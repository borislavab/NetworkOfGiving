package com.example.networkofgiving.services;

import com.example.networkofgiving.entities.Charity;
import com.example.networkofgiving.models.CharityCreationDTO;

import java.util.List;

public interface ICharityService {

    void createCharity(CharityCreationDTO charityCreationDTO);

    List<Charity> getAllCharities();
}
