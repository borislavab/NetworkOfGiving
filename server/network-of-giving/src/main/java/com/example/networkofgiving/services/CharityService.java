package com.example.networkofgiving.services;

import com.example.networkofgiving.entities.Charity;
import com.example.networkofgiving.entities.Donation;
import com.example.networkofgiving.entities.User;
import com.example.networkofgiving.entities.Volunteering;
import com.example.networkofgiving.models.CharityCreationDTO;
import com.example.networkofgiving.models.EventType;
import com.example.networkofgiving.repositories.ICharityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CharityService implements ICharityService {

    @Autowired
    private ICharityRepository charityRepository;

    @Autowired
    private IUserService userService;

    @Autowired
    private IEventService eventService;

    @Transactional
    @Override
    public void createCharity(CharityCreationDTO charityCreationDTO) throws IllegalArgumentException {
        BigDecimal amountRequired = charityCreationDTO.getAmountRequired();
        if (amountRequired.compareTo(BigDecimal.ZERO) == 0 &&
                charityCreationDTO.getVolunteersRequired() == 0) {
            throw new IllegalArgumentException();
        }
        Charity newCharity = this.constructCharityFromCharityCreationDTO(charityCreationDTO);
        Charity savedCharity = this.charityRepository.save(newCharity);
        User currentUser = this.userService.getCurrentlyAuthenticatedUser();
        String eventDescription = String.format("Created charity '%s'.", savedCharity.getTitle());
        this.eventService.addUserEvent(currentUser, savedCharity, EventType.CREATED, eventDescription);
    }

    @Transactional
    @Override
    public List<Charity> getAllCharities(Optional<String> titleFilter) {
        String filter = titleFilter.orElse("");
        return this.charityRepository.findByTitleContaining(filter);
    }

    @Override
    public Charity getCharityById(Long id) throws NoSuchElementException {
        return this.charityRepository.findById(id).get();
    }

    @Override
    public void deleteCharityById(Long id) throws AccessDeniedException {
        Optional<Charity> charityToDelete = this.charityRepository.findById(id);
        if (charityToDelete.isEmpty()) {
            return;
        }
        Charity charity = charityToDelete.get();
        User currentUser = this.userService.getCurrentlyAuthenticatedUser();
        Long userId = currentUser.getId();
        if (!userId.equals(charity.getOwnerId())) {
            throw new AccessDeniedException("Only an owner can delete a charity");
        }
        this.charityRepository.deleteById(id);
        String eventDescription = String.format("Deleted charity '%s'.", charity.getTitle());
        this.eventService.addUserEvent(currentUser, null, EventType.DELETED, eventDescription);
    }

    @Override
    public void updateCharity(Charity charity) {
        if (this.charityRepository.existsById(charity.getId())) {
            this.charityRepository.save(charity);
        }
    }

    @Transactional
    @Override
    public void editCharity(Long id, CharityCreationDTO charityCreationDTO)
            throws NoSuchElementException, AccessDeniedException {
        Charity charity = this.charityRepository.findById(id).get();
        Long userId = this.userService.getCurrentlyAuthenticatedUser().getId();
        if (!userId.equals(charity.getOwnerId())) {
            throw new AccessDeniedException("Only an owner can edit a charity");
        }
        charity.setTitle(charityCreationDTO.getTitle());
        charity.setDescription(charityCreationDTO.getDescription());
        charity.setThumbnail(charityCreationDTO.getThumbnail());
        this.changeCharityAmountRequired(charity, charityCreationDTO.getAmountRequired());
        this.changeCharityVolunteersRequired(charity, charityCreationDTO.getVolunteersRequired());
        this.charityRepository.save(charity);
    }

    @Override
    public Set<User> getCharityParticipants(Long id) {
        Optional<Charity> foundCharity = this.charityRepository.findById(id);
        if (foundCharity.isEmpty()) {
            return new HashSet<>();
        }
        Charity charity = foundCharity.get();
        Set<User> donators = charity.getDonations()
                .stream()
                .map(Donation::getDonator)
                .collect(Collectors.toSet());
        Set<User> volunteers = charity.getVolunteerings()
                .stream()
                .map(Volunteering::getUser)
                .collect(Collectors.toSet());
        donators.addAll(volunteers);
        return donators;
    }

    private void changeCharityAmountRequired(Charity charity, BigDecimal newAmount) {
        charity.setAmountRequired(newAmount);
        BigDecimal collectedAmount = charity.getAmountCollected();
        if (newAmount.compareTo(collectedAmount) >= 0) {
            return;
        }
        BigDecimal amountToDecrease = collectedAmount.subtract(newAmount);
        Set<Donation> donations = charity.getDonations();
        ArrayList<Donation> donationsList = new ArrayList<>(donations);
        donationsList.sort(Comparator.comparing(Donation::getTimestamp));
        ListIterator<Donation> donationIterator = donationsList.listIterator(donationsList.size());
        while (donationIterator.hasPrevious() && amountToDecrease.compareTo(BigDecimal.ZERO) > 0) {
            Donation toRemove = donationIterator.previous();
            amountToDecrease = amountToDecrease.subtract(toRemove.getDonationAmount());
            donations.remove(toRemove);
        }
        BigDecimal currentDonationAmount = newAmount.add(amountToDecrease);
        charity.setAmountCollected(currentDonationAmount);
    }

    private void changeCharityVolunteersRequired(Charity charity, Integer newVolunteerCount) {
        charity.setVolunteersRequired(newVolunteerCount);
        Integer currentVolunteerCount = charity.getVolunteersApplied();
        if (currentVolunteerCount <= newVolunteerCount) {
            return;
        }
        Integer volunteersToRemove = currentVolunteerCount - newVolunteerCount;
        Set<Volunteering> volunteerings = charity.getVolunteerings();
        ArrayList<Volunteering> volunteeringList = new ArrayList<>(volunteerings);
        volunteeringList.sort(Comparator.comparing(Volunteering::getTimestamp));
        int lastIndex = volunteeringList.size() - 1;
        for (int i = 0; i < volunteersToRemove; i++) {
            Volunteering volunteeringToRemove = volunteeringList.get(lastIndex - i);
            volunteerings.remove(volunteeringToRemove);
        }
        charity.setVolunteersApplied(newVolunteerCount);
    }

    public Charity constructCharityFromCharityCreationDTO(CharityCreationDTO charityCreationDTO) {
        User owner = this.userService.getCurrentlyAuthenticatedUser();
        return new Charity(
                charityCreationDTO.getTitle(),
                charityCreationDTO.getDescription(),
                charityCreationDTO.getAmountRequired(),
                charityCreationDTO.getVolunteersRequired(),
                charityCreationDTO.getThumbnail(),
                owner
        );
    }
}
