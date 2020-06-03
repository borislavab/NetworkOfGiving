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

    /**
     * Make a donation to a charity
     *
     * @param charityId         The id of the charity
     * @param donationAmountDTO Contains the donation amount
     *
     * @return
     * 201 CREATED status code on success
     * 403 FORBIDDEN status code if user is not authenticated
     * 404 NOT_FOUND status code on invalid charity id.
     * 400 BAD_REQUEST status code if the donated amount is greater than
     * the amount the charity remains to collect.
     */
    @PostMapping("{charityId}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void donateToCharity(@NotNull @PathVariable Long charityId,
                                @NotNull @Valid @RequestBody DonationAmountDTO donationAmountDTO) {
        this.donationService.donateToCharity(charityId, donationAmountDTO);
    }

    /**
     * Get a customized prediction of the amount to donate to a specific charity
     *
     * @param charityId The id of the charity
     *
     * @return
     * 200 OK status code along with an object containing the predicted amount
     * 403 FORBIDDEN status code if user is not authenticated
     * 404 NOT_FOUND status code on invalid charity id.
     */
    @GetMapping("{charityId}/prediction")
    public DonationAmountDTO getDonationPrediction(@NotNull @PathVariable Long charityId) {
        return this.donationService.getDonationPrediction(charityId);
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
