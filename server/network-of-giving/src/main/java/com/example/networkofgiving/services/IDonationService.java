package com.example.networkofgiving.services;

import com.example.networkofgiving.models.DonationAmountDTO;

import java.util.NoSuchElementException;

public interface IDonationService {

    void donateToCharity(Long charityId, DonationAmountDTO donationAmountDTO)
            throws NoSuchElementException, IllegalArgumentException;
}
