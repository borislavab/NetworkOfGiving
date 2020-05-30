package com.example.networkofgiving.controllers;

import com.example.networkofgiving.entities.Volunteering;
import com.example.networkofgiving.models.VolunteerDTO;
import com.example.networkofgiving.services.IVolunteeringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/charities/volunteer")
public class VolunteeringController {

    @Autowired
    private IVolunteeringService volunteeringService;

    @PostMapping("{charityId}")
    @ResponseStatus(code = HttpStatus.CREATED)
    void volunteerToCharity(@NotNull @PathVariable Long charityId) {
        this.volunteeringService.volunteerToCharity(charityId);
    }

    @GetMapping("{charityId}/me")
    VolunteerDTO getUserVolunteering(@NotNull @PathVariable Long charityId) {
        Volunteering volunteering = this.volunteeringService.getUserVolunteering(charityId);
        return new VolunteerDTO(volunteering);
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            NoSuchElementException.class
    })
    public void notFound() {
    }
}
