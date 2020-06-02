import { Component, OnInit } from '@angular/core';
import { CharityCreationModel } from '../../models/charity-creation.model';
import { CharityService } from '../../services/charity.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Charity } from '../../models/charity.model';

@Component({
    selector: 'app-edit-charity',
    templateUrl: './edit-charity.component.html',
    styleUrls: ['./edit-charity.component.scss']
})
export class EditCharityComponent implements OnInit {

    charityPrepopulatedValues: CharityCreationModel;
    charity: Charity;
    editFailed = false;

    constructor(private charityService: CharityService,
                private router: Router,
                route: ActivatedRoute) {
        const charityId = Number(route.snapshot.params.id);
        if (isNaN(charityId)) {
            this.navigateToNotFound();
        }
        this.charityService.getCharityById(charityId)
            .subscribe(charity => {
                this.initializeCharity(charity);
            }, () => {
                this.navigateToNotFound();
            });
    }

    ngOnInit(): void {
    }

    navigateToNotFound() {
        this.router.navigate(['not-found']);
    }

    initializeCharity(charity: Charity) {
        this.charity = charity;
        this.charityPrepopulatedValues = {
            title: charity.title,
            description: charity.description,
            amountRequired: charity.amountRequired,
            volunteersRequired: charity.volunteersRequired,
            thumbnail: charity.thumbnail
        };
    }

    editCharity(editedCharity: CharityCreationModel) {
        this.charityService.editCharity(this.charity.id, editedCharity)
            .subscribe(() => {
                this.router.navigate(['/charities']);
            }, () => {
                this.editFailed = true;
            });
    }

}
