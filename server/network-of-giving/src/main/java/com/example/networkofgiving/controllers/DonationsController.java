package com.example.networkofgiving.controllers;

import com.example.networkofgiving.models.DonationAmountDTO;
import com.example.networkofgiving.services.IDonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/charities/donate")
public class DonationsController {

    @Autowired
    IDonationService donationService;

    @PostMapping("{charityId}")
    void donateToCharity(@NotNull @PathVariable Long charityId,
                         @NotNull @Valid @RequestBody DonationAmountDTO donationAmountDTO) {
        this.donationService.donateToCharity(charityId, donationAmountDTO);
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
