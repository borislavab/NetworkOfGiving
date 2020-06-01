package com.example.networkofgiving.controllers;

import com.example.networkofgiving.services.IDonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/charities/donate")
public class DonationsController {

    @Autowired
    IDonationService donationService;
}
