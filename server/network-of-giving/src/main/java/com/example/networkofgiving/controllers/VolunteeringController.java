package com.example.networkofgiving.controllers;

import com.example.networkofgiving.services.IVolunteeringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/charities/{charityId}/volunteer")
public class VolunteeringController {

    @Autowired
    private IVolunteeringService volunteeringService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    void volunteerToCharity(@NotNull @PathVariable Long charityId) {
        this.volunteeringService.volunteerToCharity(charityId);
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            NoSuchElementException.class
    })
    public void notFound() {
    }
}
