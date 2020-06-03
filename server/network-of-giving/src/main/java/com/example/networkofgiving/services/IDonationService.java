package com.example.networkofgiving.services;

import com.example.networkofgiving.models.DonationAmountDTO;

import java.util.NoSuchElementException;

public interface IDonationService {

    /**
     * Make a donation to a charity
     *
     * @param charityId         The id of the charity
     * @param donationAmountDTO Contains the donation amount
     *
     * @throws NoSuchElementException on invalid charity id.
     * @throws IllegalArgumentException if the donated amount is greater than
     * the amount the charity remains to collect.
     */
    void donateToCharity(Long charityId, DonationAmountDTO donationAmountDTO)
            throws NoSuchElementException, IllegalArgumentException;

    /**
     * Get current user's customized prediction of the amount to donate to a specific charity
     *
     * @param charityId The id of the charity
     *
     * @return the predicted donation amount
     * @throws NoSuchElementException on invalid charity id.
     */
    DonationAmountDTO getDonationPrediction(Long charityId) throws NoSuchElementException;
}
