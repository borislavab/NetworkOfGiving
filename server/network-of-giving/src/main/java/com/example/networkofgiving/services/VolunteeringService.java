package com.example.networkofgiving.services;

import com.example.networkofgiving.entities.Charity;
import com.example.networkofgiving.entities.User;
import com.example.networkofgiving.entities.Volunteering;
import com.example.networkofgiving.entities.VolunteeringKey;
import com.example.networkofgiving.models.EventType;
import com.example.networkofgiving.repositories.IVolunteeringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class VolunteeringService implements IVolunteeringService {

    @Autowired
    private IVolunteeringRepository volunteeringRepository;

    @Autowired
    private ICharityService charityService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IEventService eventService;

    @Transactional
    @Override
    public void volunteerToCharity(Long charityId) throws NoSuchElementException, IllegalArgumentException {
        Charity charity = this.charityService.getCharityById(charityId);
        if (charity.getVolunteersApplied() >= charity.getVolunteersRequired()) {
            throw new IllegalArgumentException("Volunteer goal reached.");
        }
        User currentUser = this.userService.getCurrentlyAuthenticatedUser();
        VolunteeringKey volunteeringKey = new VolunteeringKey(currentUser.getId(), charity.getId());
        if (this.volunteeringRepository.existsById(volunteeringKey)) {
            throw new IllegalArgumentException("A user cannot volunteer more than once.");
        }
        Volunteering newVolunteering = new Volunteering(currentUser, charity);
        this.volunteeringRepository.save(newVolunteering);
        charity.setVolunteersApplied(charity.getVolunteersApplied() + 1);
        this.charityService.updateCharity(charity);

        String eventDescription = String.format("Volunteered to charity '%s'.", charity.getTitle());
        this.eventService.addUserEvent(currentUser, charity, EventType.VOLUNTEERED, eventDescription);

        if (charity.getVolunteersApplied().equals(charity.getVolunteersRequired())) {
            eventDescription = String.format("Charity '%s' gathered all volunteers needed!", charity.getTitle());
            this.eventService.addCharityEvent(charity, EventType.REACHED_VOLUNTEER_GOAL, eventDescription);
        }
    }

    @Transactional
    @Override
    public Volunteering getUserVolunteering(Long charityId) throws NoSuchElementException {
        User currentUser = this.userService.getCurrentlyAuthenticatedUser();
        VolunteeringKey volunteeringKey = new VolunteeringKey(currentUser.getId(), charityId);
        return this.volunteeringRepository.findById(volunteeringKey).get();
    }

    @Override
    public void unvolunteer(VolunteeringKey volunteeringId) {
        this.volunteeringRepository.deleteById(volunteeringId);
    }
}
