package com.example.networkofgiving.services;

import com.example.networkofgiving.repositories.IDonationRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class DonationService implements IDonationService {

    @Autowired
    private IDonationRepository donationRepository;
}
