package com.example.networkofgiving.services;

import com.example.networkofgiving.entities.Charity;
import com.example.networkofgiving.entities.Donation;
import com.example.networkofgiving.entities.User;
import com.example.networkofgiving.entities.Volunteering;
import com.example.networkofgiving.models.CharityCreationDTO;
import com.example.networkofgiving.models.EventType;
import com.example.networkofgiving.repositories.ICharityRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.access.AccessDeniedException;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;


public class CharityServiceTest {

    @InjectMocks
    CharityService charityService;

    @Mock
    ICharityRepository charityRepository;

    @Mock
    IUserService userService;

    @Mock
    IEventService eventService;

    Long id;

    User user;

    Charity charity;

    @Before
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

    @Test(expected = IllegalArgumentException.class)
    public void createCharity_NoResourcesRequired_ThrowsException() {
        CharityCreationDTO charityCreationDTO = new CharityCreationDTO();
        charityCreationDTO.setAmountRequired(new BigDecimal(0.0));
        charityCreationDTO.setVolunteersRequired(0);

        this.charityService.createCharity(charityCreationDTO);

        verifyNoInteractions(this.charityRepository);
        verifyNoInteractions(this.eventService);
    }

    @Test
    public void createCharity_ResourcesRequired_CreatesCharityAndLogsEvent() {
        CharityCreationDTO charityCreationDTO = new CharityCreationDTO();
        charityCreationDTO.setAmountRequired(new BigDecimal(10.0));
        charityCreationDTO.setTitle("title");
        charityCreationDTO.setDescription("description");
        charityCreationDTO.setThumbnail(null);
        charityCreationDTO.setVolunteersRequired(0);
        when(this.userService.getCurrentlyAuthenticatedUser()).thenReturn(this.user);
        Charity savedCharity = this.charityService.constructCharityFromCharityCreationDTO(charityCreationDTO);
        when(this.charityRepository.save(any())).thenReturn(savedCharity);

        this.charityService.createCharity(charityCreationDTO);

        verify(this.charityRepository).save(any());
        String eventDescription = "Created charity 'title'.";
        verify(this.eventService).addUserEvent(this.user, savedCharity, EventType.CREATED, eventDescription);
    }

    @Test
    public void getAllCharities_NoFilter_ReturnsAll() {
        Optional<String> titleFilter = Optional.empty();

        this.charityService.getAllCharities(titleFilter);

        verify(this.charityRepository).findByTitleContaining("");
    }

    @Test
    public void getAllCharities_WithFilter_PassesFilter() {
        String filter = "filter";
        Optional<String> titleFilter = Optional.of(filter);

        this.charityService.getAllCharities(titleFilter);

        verify(this.charityRepository).findByTitleContaining(filter);
    }

    @Test
    public void getCharityById_ExistingCharity_ReturnsCharity() {
        Optional<Charity> expected = Optional.of(this.charity);
        Mockito.when(this.charityRepository.findById(id)).thenReturn(expected);

        Charity result = this.charityService.getCharityById(id);

        Assert.assertEquals(charity, result);
    }

    @Test(expected = NoSuchElementException.class)
    public void getCharityById_NonExistingCharity_ThrowsException() {
        Optional<Charity> expected = Optional.empty();
        Mockito.when(this.charityRepository.findById(id)).thenReturn(expected);

        Charity result = this.charityService.getCharityById(id);
    }

    @Test
    public void deleteCharityById_ExistingCharityAndUserIsOwner_DeletesAndLogsEvent() {
        Optional<Charity> charityResult = Optional.of(this.charity);
        Mockito.when(this.charityRepository.findById(id)).thenReturn(charityResult);
        Mockito.when(this.userService.getCurrentlyAuthenticatedUser()).thenReturn(this.user);

        this.charityService.deleteCharityById(id);

        verify(this.charityRepository).deleteById(id);
        String eventDescription = "Deleted charity '" + this.charity.getTitle() + "'.";
        verify(this.eventService).addUserEvent(this.user, null, EventType.DELETED, eventDescription);
    }

    @Test(expected = AccessDeniedException.class)
    public void deleteCharityById_ExistingCharityAndUserNotOwner_ThrowsException() {
        this.charity.setOwnerId(5L);
        Optional<Charity> charityResult = Optional.of(this.charity);
        Mockito.when(this.charityRepository.findById(id)).thenReturn(charityResult);
        Mockito.when(this.userService.getCurrentlyAuthenticatedUser()).thenReturn(this.user);

        this.charityService.deleteCharityById(id);
    }

    @Test
    public void deleteCharityById_NonExistingCharity_DoesNothing() {
        Optional<Charity> charityResult = Optional.empty();
        Mockito.when(this.charityRepository.findById(id)).thenReturn(charityResult);

        this.charityService.deleteCharityById(id);

        verify(this.charityRepository).findById(id);
        verifyNoMoreInteractions(this.charityRepository);
        verifyNoInteractions(this.userService);
        verifyNoInteractions(this.eventService);
    }

    @Test
    public void updateCharity_NonExistingCharity_NothingSaved() {
        when(this.charityRepository.existsById(this.id)).thenReturn(false);

        this.charityService.updateCharity(this.charity);

        verify(this.charityRepository).existsById(this.id);
        verifyNoMoreInteractions(this.charityRepository);
    }

    @Test
    public void updateCharity_ExistingCharity_Saved() {
        when(this.charityRepository.existsById(this.id)).thenReturn(true);

        this.charityService.updateCharity(this.charity);

        verify(this.charityRepository).existsById(this.id);
        verify(this.charityRepository).save(this.charity);
    }

    @Test
    public void getCharityParticipants_UserWithMultipleParticipations_IncludesUserOnce() {
        this.charity.setDonations(new HashSet<>(
                List.of(new Donation(this.user, this.charity, new BigDecimal(6.00)))
        ));

        this.charity.setVolunteerings(new HashSet<>(
                List.of(new Volunteering(this.user, this.charity))
        ));

        when(this.charityRepository.findById(this.id))
                .thenReturn(Optional.of(this.charity));

        Set<User> result = this.charityService.getCharityParticipants(this.id);

        assertEquals(1, result.size());
        assertTrue(result.contains(this.user));
    }

    @Test
    public void getCharityParticipants_MultipleUsers_ReturnsAllUsers() {
        User anotherUser = new User(10L,
                "username10",
                "password10",
                "Oliver",
                58,
                null,
                null
        );

        this.charity.setDonations(new HashSet<>(
                List.of(new Donation(this.user, this.charity, new BigDecimal(6.00)))
        ));

        this.charity.setVolunteerings(new HashSet<>(
                List.of(new Volunteering(this.user, this.charity),
                        new Volunteering(anotherUser, this.charity))
        ));

        when(this.charityRepository.findById(this.id))
                .thenReturn(Optional.of(this.charity));

        Set<User> result = this.charityService.getCharityParticipants(this.id);

        assertEquals(2, result.size());
        assertTrue(result.contains(this.user));
        assertTrue(result.contains(anotherUser));
    }

    @Test(expected = AccessDeniedException.class)
    public void editCharity_UserNotOwner_ThrowsException() {
        this.charity.setOwnerId(10L);
        Optional<Charity> expected = Optional.of(this.charity);
        Mockito.when(this.charityRepository.findById(id)).thenReturn(expected);
        when(this.userService.getCurrentlyAuthenticatedUser()).thenReturn(this.user);

        this.charityService.editCharity(this.id, null);
    }
}