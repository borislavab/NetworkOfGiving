package com.example.networkofgiving.services;

import com.example.networkofgiving.entities.Charity;
import com.example.networkofgiving.entities.Event;
import com.example.networkofgiving.entities.User;
import com.example.networkofgiving.models.EventType;
import com.example.networkofgiving.repositories.IEventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EventServiceTest {

    @InjectMocks
    EventService eventService;

    @Mock
    IEventRepository eventRepository;

    @Mock
    IUserService userService;

    @Mock
    ICharityService charityService;

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
    void addUserEvent_SavesEvent() {
        this.eventService.addUserEvent(
                this.user,
                this.charity,
                EventType.VOLUNTEERED,
                "description"
        );
        verify(this.eventRepository).save(any());
    }

    @Test
    void addCharityEvent_NoParticipants_SavesNothing() {
        when(this.charityService.getCharityParticipants(this.charity.getId()))
                .thenReturn(new HashSet<>());
        this.eventService.addCharityEvent(
                this.charity,
                EventType.VOLUNTEERED,
                "description");
        verifyNoInteractions(this.eventRepository);
    }

    @Test
    void addCharityEvent_MultipleParticipants_AddsEntryForEachParticipant() {
        User anotherUser = new User(10L,
                "username10",
                "password10",
                "Oliver",
                58,
                null,
                null
        );
        when(this.charityService.getCharityParticipants(this.charity.getId()))
                .thenReturn(new HashSet<>(
                        List.of(
                                this.user, anotherUser

                        )
                ));
        this.eventService.addCharityEvent(
                this.charity,
                EventType.VOLUNTEERED,
                "description");
        verify(this.eventRepository, times(2)).save(any());
    }

    @Test
    void getUserEvents_ReturnsWhatTheRepositoryDoes() {
        when(this.userService.getCurrentlyAuthenticatedUser())
                .thenReturn(this.user);
        when(this.eventRepository.findAllByUserId(eq(this.user.getId()), any()))
                .thenReturn(new ArrayList<>());
        List<Event> userEvents = this.eventService.getUserEvents();
        verify(this.eventRepository).findAllByUserId(eq(this.user.getId()), any());
        assertEquals(new LinkedList<>(), userEvents);
    }
}