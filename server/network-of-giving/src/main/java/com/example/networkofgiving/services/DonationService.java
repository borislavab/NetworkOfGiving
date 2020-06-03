package com.example.networkofgiving.services;

import com.example.networkofgiving.entities.Charity;
import com.example.networkofgiving.entities.Donation;
import com.example.networkofgiving.entities.User;
import com.example.networkofgiving.models.DonationAmountDTO;
import com.example.networkofgiving.models.EventType;
import com.example.networkofgiving.repositories.IDonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class DonationService implements IDonationService {

    @Autowired
    private IDonationRepository donationRepository;

    @Autowired
    private IUserService userService;

    @Autowired
    private ICharityService charityService;

    @Autowired
    private IEventService eventService;

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

        String eventDescription = String.format("Donated $%.2f to charity '%s'.", donationAmount, charity.getTitle());
        this.eventService.addUserEvent(currentUser, charity, EventType.DONATED, eventDescription);

        if (newAmount.compareTo(charity.getAmountRequired()) == 0) {
            eventDescription = String.format("Charity '%s' collected all money required!", charity.getTitle());
            this.eventService.addCharityEvent(charity, EventType.REACHED_AMOUNT_GOAL, eventDescription);
        }
    }

    @Override
    public DonationAmountDTO getDonationPrediction(Long charityId) throws
            NoSuchElementException {
        Set<Donation> donations = this.userService.getUserDonations();
        Charity charity = this.charityService.getCharityById(charityId);
        double average = donations.stream()
                .map(Donation::getDonationAmount)
                .mapToDouble(BigDecimal::doubleValue)
                .average()
                .orElse(0.01);
        BigDecimal averageAmount = new BigDecimal(average);
        BigDecimal amountToCollect = charity.getAmountRequired().subtract(charity.getAmountCollected());
        BigDecimal prediction = averageAmount.min(amountToCollect);
        return new DonationAmountDTO(prediction);
    }

    @Override
    public void refundDonation(Long donationId) {
        this.donationRepository.deleteById(donationId);
    }
}
