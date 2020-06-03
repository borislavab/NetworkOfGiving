package com.example.networkofgiving.services;

import com.example.networkofgiving.entities.Charity;
import com.example.networkofgiving.entities.Donation;
import com.example.networkofgiving.entities.User;
import com.example.networkofgiving.models.DonationAmountDTO;
import com.example.networkofgiving.models.EventType;
import com.example.networkofgiving.repositories.IDonationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DonationServiceTest {

    @InjectMocks
    DonationService donationService;

    @Mock
    IDonationRepository donationRepository;

    @Mock
    IUserService userService;

    @Mock
    ICharityService charityService;

    @Mock
    IEventService eventService;

    Long id;
    User user;
    Charity charity;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        this.id = 1L;

        this.user = new User(this.id, "username", "password", "John", 24, null, null);

        this.charity = new Charity("Some charity",
                "Description",
                new BigDecimal(10.0),
                10,
                null,
                this.user);
        this.charity.setId(this.id);
        this.charity.setOwnerId(this.user.getId());
    }

    @Test
    void donateToCharity_DonationAmountTooBig_ThrowsException() {
        this.charity.setAmountRequired(new BigDecimal(10.0));
        this.charity.setAmountCollected(new BigDecimal(5.0));
        when(this.charityService.getCharityById(this.id))
                .thenReturn(this.charity);

        DonationAmountDTO amountDTO = new DonationAmountDTO(new BigDecimal(100));

        assertThrows(IllegalArgumentException.class, () ->
                this.donationService.donateToCharity(this.id, amountDTO));
    }

    @Test
    void donateToCharity_DonationAmountAcceptable_SavesDonationAndLogs() {
        this.charity.setAmountRequired(new BigDecimal(10.0));
        this.charity.setAmountCollected(new BigDecimal(5.0));
        when(this.charityService.getCharityById(this.id))
                .thenReturn(this.charity);
        DonationAmountDTO amountDTO = new DonationAmountDTO(new BigDecimal(4.0));
        when(this.userService.getCurrentlyAuthenticatedUser())
                .thenReturn(this.user);

        this.donationService.donateToCharity(this.id, amountDTO);

        verify(this.donationRepository).save(any());
        assertEquals(new BigDecimal(9.0), this.charity.getAmountCollected());
        verify(this.charityService).updateCharity(any());
        String eventDescription = "Donated $4.00 to charity '" + charity.getTitle() + "'.";
        verify(this.eventService).addUserEvent(this.user, charity, EventType.DONATED, eventDescription);
        verifyNoMoreInteractions(this.eventService);
    }

    @Test
    void donateToCharity_DonationCompletesGoal_SavesDonationAndLogs() {
        this.charity.setAmountRequired(new BigDecimal(10.0));
        this.charity.setAmountCollected(new BigDecimal(5.0));
        when(this.charityService.getCharityById(this.id))
                .thenReturn(this.charity);
        DonationAmountDTO amountDTO = new DonationAmountDTO(new BigDecimal(5.0));
        when(this.userService.getCurrentlyAuthenticatedUser())
                .thenReturn(this.user);

        this.donationService.donateToCharity(this.id, amountDTO);

        verify(this.donationRepository).save(any());
        assertEquals(new BigDecimal(10.0), this.charity.getAmountCollected());
        verify(this.charityService).updateCharity(any());
        String eventDescription = "Donated $5.00 to charity '" + charity.getTitle() + "'.";
        verify(this.eventService).addUserEvent(this.user, charity, EventType.DONATED, eventDescription);

        eventDescription = "Charity '" + charity.getTitle() + "' collected all money required!";
        verify(this.eventService).addCharityEvent(charity, EventType.REACHED_AMOUNT_GOAL, eventDescription);
    }


    @Test
    void getDonationPrediction_NoUserDonations_ReturnsMinimalDonation() {
        when(this.userService.getUserDonations())
                .thenReturn(new HashSet<>());
        when(this.charityService.getCharityById(this.id))
                .thenReturn(this.charity);

        DonationAmountDTO donationPrediction = this.donationService.getDonationPrediction(this.id);

        assertEquals(new BigDecimal(0.01), donationPrediction.getAmount());
    }

    @Test
    void getDonationPrediction_AverageDonationIsValidDonation_ReturnsAverageDonation() {
        when(this.userService.getUserDonations())
                .thenReturn(new HashSet<>(
                        List.of(
                                new Donation(this.user, this.charity, new BigDecimal(5)),
                                new Donation(this.user, this.charity, new BigDecimal(4))
                        )
                ));
        when(this.charityService.getCharityById(this.id))
                .thenReturn(this.charity);

        DonationAmountDTO donationPrediction = this.donationService.getDonationPrediction(this.id);

        assertEquals(new BigDecimal(4.50), donationPrediction.getAmount());
    }

    @Test
    void getDonationPrediction_AverageDonationIsNotValidDonation_RemainingCharityAmount() {
        when(this.userService.getUserDonations())
                .thenReturn(new HashSet<>(
                        List.of(
                                new Donation(this.user, this.charity, new BigDecimal(100)),
                                new Donation(this.user, this.charity, new BigDecimal(200))
                        )
                ));
        this.charity.setAmountRequired(new BigDecimal(10.0));
        this.charity.setAmountCollected(new BigDecimal(5.0));
        when(this.charityService.getCharityById(this.id))
                .thenReturn(this.charity);

        DonationAmountDTO donationPrediction = this.donationService.getDonationPrediction(this.id);

        assertEquals(new BigDecimal(5.00), donationPrediction.getAmount());
    }
}