package com.example.networkofgiving.services;

import com.example.networkofgiving.entities.Charity;
import com.example.networkofgiving.entities.Donation;
import com.example.networkofgiving.entities.User;
import com.example.networkofgiving.models.DonationAmountDTO;
import com.example.networkofgiving.repositories.IDonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.NoSuchElementException;

@Service
public class DonationService implements IDonationService {

    @Autowired
    private IDonationRepository donationRepository;

    @Autowired
    private IUserService userService;

    @Autowired
    private ICharityService charityService;

    @Transactional
    @Override
    public void donateToCharity(Long charityId, DonationAmountDTO donationAmountDTO)
            throws NoSuchElementException, IllegalArgumentException {
        Charity charity = charityService.getCharityById(charityId);
        BigDecimal donationAmount = donationAmountDTO.getAmount();
        BigDecimal newAmount = charity.getAmountCollected().add(donationAmount);

        if (newAmount.compareTo(charity.getAmountRequired()) > 0) {
            throw new IllegalArgumentException("Cannot exceed donation goal.");
        }

        User currentUser = this.userService.getCurrentlyAuthenticatedUser();
        Donation donation = new Donation(currentUser, charity, donationAmount);
        this.donationRepository.save(donation);

        charity.setAmountCollected(newAmount);
        this.charityService.updateCharity(charity);
    }
}