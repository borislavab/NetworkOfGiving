import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CharityService } from '../charity.service';
import { Charity } from '../models/charity.model';
import { AuthenticationService } from 'src/app/authentication/authentication.module';

@Component({
    selector: 'app-charity',
    templateUrl: './charity.component.html',
    styleUrls: ['./charity.component.scss']
})
export class CharityComponent implements OnInit {

    id: number;
    charity: Charity;
    shouldDeleteDialogOpen = false;
    deletionFailed = false;

    constructor(route: ActivatedRoute,
                private router: Router,
                private charityService: CharityService,
                private authService: AuthenticationService) {
        this.id = Number(route.snapshot.params.id);
        if (isNaN(this.id)) {
            this.navigateToNotFound();
        }
        this.charityService.getCharityById(this.id)
            .subscribe(charity => {
                this.initializeCharity(charity);
            }, () => {
                this.navigateToNotFound();
            });
    }

    ngOnInit(): void { }

    initializeCharity(charity: Charity) {
        this.charity = charity;
        if (!this.charity.thumbnail) {
            this.charity.thumbnail = 'assets/missing-image.png';
        }
    }

    onDeleteClicked(): void {
        this.openConfirmationDialog();
    }
    
    openConfirmationDialog() {
        this.shouldDeleteDialogOpen = true;
    }

    deleteCharity(): void {
        this.charityService.deleteCharity(this.id)
            .subscribe(
                () => this.navigateToHome(),
                () => this.deletionFailed = true
            );
    }

    userIsOwner(): boolean {
        return this.authService.currentUser.id === this.charity.ownerId;
    }

    navigateToNotFound() {
        this.router.navigate(['not-found']);
    }

    navigateToHome() {
        this.router.navigate(['/']);
    }
}
