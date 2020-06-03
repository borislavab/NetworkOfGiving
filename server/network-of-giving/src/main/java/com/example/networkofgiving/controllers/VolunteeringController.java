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

    /**
     * Volunteer to a charity
     *
     * @param charityId The id of the charity
     * @return
     * 201 CREATED status code on success
     * 403 FORBIDDEN status code if user is not authenticated
     * 404 NOT_FOUND status code on invalid charity id.
     * 400 BAD_REQUEST status code if the charity is not looking for more volunteers
     * or the requesting user is already a volunteer for the charity.
     */
    @PostMapping("{charityId}")
    @ResponseStatus(code = HttpStatus.CREATED)
    void volunteerToCharity(@NotNull @PathVariable Long charityId) {
        this.volunteeringService.volunteerToCharity(charityId);
    }

    /**
     * Get information about the current user's volunteering status in a particular charity
     *
     * @param charityId The id of the charity
     *
     * @return
     * 200 OK status code if the user is a volunteer in the charity, along with
     * information about the volunteering
     * 403 FORBIDDEN status code if user is not authenticated
     * 404 NOT_FOUND status code if user is not a volunteer in such a charity
     */
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

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            IllegalArgumentException.class
    })
    public void badRequest() {
    }
}
