import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, ValidationErrors, ValidatorFn } from '@angular/forms';
import { Router } from '@angular/router';

import { CharityService } from '../../services/charity.service';
import { CharityCreationModel } from '../../models/charity-creation.model';

@Component({
    selector: 'app-create-charity',
    templateUrl: './create-charity.component.html',
    styleUrls: ['./create-charity.component.scss']
})
export class CreateCharityComponent implements OnInit {

    creationFailed = false;
    defaultValues: CharityCreationModel = {
        title: undefined,
        description: undefined,
        amountRequired: 0.0,
        volunteersRequired: 0,
        thumbnail: undefined
    };

    constructor(private charityService: CharityService,
                private router: Router) { }

    ngOnInit(): void { }

    createCharity(charity: CharityCreationModel) {
        this.charityService.createCharity(charity)
            .subscribe(
                () => this.router.navigate(['/charities']),
                () => this.creationFailed = true
            );
    }
}
