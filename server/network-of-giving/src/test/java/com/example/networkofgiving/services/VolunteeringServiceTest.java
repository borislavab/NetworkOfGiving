package com.example.networkofgiving.services;

import com.example.networkofgiving.entities.Charity;
import com.example.networkofgiving.entities.User;
import com.example.networkofgiving.entities.Volunteering;
import com.example.networkofgiving.entities.VolunteeringKey;
import com.example.networkofgiving.models.EventType;
import com.example.networkofgiving.repositories.IVolunteeringRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class VolunteeringServiceTest {

    @InjectMocks
    VolunteeringService volunteeringService;

    @Mock
    IVolunteeringRepository volunteeringRepository;

    @Mock
    ICharityService charityService;

    @Mock
    IUserService userService;

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
    void volunteerToCharity_CharityNotLookingForVolunteers_ThrowsException() {
        this.charity.setVolunteersRequired(5);
        this.charity.setVolunteersApplied(5);
        when(this.charityService.getCharityById(this.charity.getId()))
                .thenReturn(this.charity);

        assertThrows(IllegalArgumentException.class, () -> {
            this.volunteeringService.volunteerToCharity(this.charity.getId());
        });
    }

    @Test
    void volunteerToCharity_NonExistingCharity_ThrowsException() {
        when(this.charityService.getCharityById(this.charity.getId()))
                .thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () -> {
            this.volunteeringService.volunteerToCharity(this.charity.getId());
        });
    }

    @Test
    void volunteerToCharity_UserAlreadyVolunteered_ThrowsException() {
        charity.setVolunteersRequired(4);
        charity.setVolunteersApplied(0);
        when(this.charityService.getCharityById(this.charity.getId()))
                .thenReturn(this.charity);
        when(this.userService.getCurrentlyAuthenticatedUser())
                .thenReturn(this.user);
        VolunteeringKey key = new VolunteeringKey(
                this.user.getId(),
                this.charity.getId()
        );
        when(this.volunteeringRepository.existsById(key))
                .thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> {
            this.volunteeringService.volunteerToCharity(this.charity.getId());
        });
    }

    @Test
    void volunteerToCharity_ValidVolunteering_SavesAndLogsVolunteering() {
        charity.setVolunteersRequired(4);
        charity.setVolunteersApplied(0);
        when(this.charityService.getCharityById(this.charity.getId()))
                .thenReturn(this.charity);
        when(this.userService.getCurrentlyAuthenticatedUser())
                .thenReturn(this.user);
        VolunteeringKey key = new VolunteeringKey(
                this.user.getId(),
                this.charity.getId()
        );
        when(this.volunteeringRepository.existsById(key))
                .thenReturn(false);

        this.volunteeringService.volunteerToCharity(this.charity.getId());

        verify(this.volunteeringRepository).save(any());
        assertEquals(1, this.charity.getVolunteersApplied());
        verify(this.charityService).updateCharity(this.charity);

        String eventDescription = "Volunteered to charity '" + this.charity.getTitle() + "'.";
        verify(this.eventService).addUserEvent(this.user, this.charity, EventType.VOLUNTEERED, eventDescription);
    }

    @Test
    void volunteerToCharity_VolunteeringCompletesGoal_CreatesCharityEvent() {
        charity.setVolunteersRequired(4);
        charity.setVolunteersApplied(3);
        when(this.charityService.getCharityById(this.charity.getId()))
                .thenReturn(this.charity);
        when(this.userService.getCurrentlyAuthenticatedUser())
                .thenReturn(this.user);
        VolunteeringKey key = new VolunteeringKey(
                this.user.getId(),
                this.charity.getId()
        );
        when(this.volunteeringRepository.existsById(key))
                .thenReturn(false);

        this.volunteeringService.volunteerToCharity(this.charity.getId());

        verify(this.volunteeringRepository).save(any());
        assertEquals(4, this.charity.getVolunteersApplied());
        verify(this.charityService).updateCharity(this.charity);

        String eventDescription = "Volunteered to charity '" + this.charity.getTitle() + "'.";
        verify(this.eventService).addUserEvent(this.user, this.charity, EventType.VOLUNTEERED, eventDescription);

        eventDescription = "Charity '" + this.charity.getTitle() + "' gathered all volunteers needed!";
        verify(this.eventService).addCharityEvent(this.charity, EventType.REACHED_VOLUNTEER_GOAL, eventDescription);
    }

    @Test
    void getUserVolunteering_ExistingVolunteering_ReturnsVolunteering() {
        when(this.userService.getCurrentlyAuthenticatedUser())
                .thenReturn(this.user);
        VolunteeringKey key = new VolunteeringKey(
                this.user.getId(),
                this.charity.getId()
        );
        Volunteering volunteering = new Volunteering(this.user, this.charity);
        when(this.volunteeringRepository.findById(key))
                .thenReturn(Optional.of(volunteering));
        Volunteering result = this.volunteeringService.getUserVolunteering(this.id);
        verify(this.volunteeringRepository).findById(key);
        assertEquals(volunteering, result);
    }

    @Test
    void getUserVolunteering_NonExistingVolunteering_ThrowsException() {
        when(this.userService.getCurrentlyAuthenticatedUser())
                .thenReturn(this.user);
        VolunteeringKey key = new VolunteeringKey(
                this.user.getId(),
                this.charity.getId()
        );
        when(this.volunteeringRepository.findById(key))
                .thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> {
            this.volunteeringService.getUserVolunteering(this.id);
        });
    }
}