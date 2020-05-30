import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CharityService } from '../services/charity.service';
import { AuthenticationService } from 'src/app/authentication/authentication.module';
import { CharityDetails } from '../models/charity-details.model';
import { VolunteerService } from '../services/volunteer.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
    selector: 'app-charity',
    templateUrl: './charity.component.html',
    styleUrls: ['./charity.component.scss']
})
export class CharityComponent implements OnInit {

    id: number;
    charity: CharityDetails;
    userHasVolunteered = true;
    shouldDeleteDialogOpen = false;
    shouldVoluteerDialogOpen = false;
    actionFailed = false;
    failedAction: string;

    constructor(route: ActivatedRoute,
                private router: Router,
                private charityService: CharityService,
                private authService: AuthenticationService,
                private volunteerService: VolunteerService) {
        this.id = Number(route.snapshot.params.id);
        if (isNaN(this.id)) {
            this.navigateToNotFound();
        }
        this.loadData();
    }

    ngOnInit(): void { }

    initializeCharity(charity: CharityDetails) {
        this.charity = charity;
        if (!this.charity.thumbnail) {
            this.charity.thumbnail = 'assets/missing-image.png';
        }
    }

    onDeleteClicked(): void {
        this.shouldDeleteDialogOpen = true;
    }

    deleteCharity(): void {
        this.charityService.deleteCharity(this.id)
            .subscribe(
                () => this.navigateToHome(),
                () => this.showFailure('delete')
            );
    }

    userIsOwner(): boolean {
        return this.authService.currentUser
            && this.authService.currentUser.id === this.charity.ownerId;
    }

    userIsAuthenticated(): boolean {
        return this.authService.isAuthenticated();
    }

    navigateToNotFound() {
        this.router.navigate(['not-found']);
    }

    navigateToHome() {
        this.router.navigate(['/']);
    }

    donateClicked() {

    }

    volunteerClicked() {
        this.shouldVoluteerDialogOpen = true;
    }

    volunteer() {
        this.shouldVoluteerDialogOpen = false;
        this.volunteerService.volunteerToCharity(this.charity.id)
            .subscribe(
                () => this.loadData(),
                () => this.showFailure('volunteer')
            );
    }

    isVolunteeringDisabled(): boolean {
        return this.hasReachedVolunteersGoal()
            || this.userHasVolunteered;
    }

    hasReachedDonationsGoal(): boolean {
        return this.charity.amountCollected === this.charity.amountRequired;
    }

    hasReachedVolunteersGoal(): boolean {
        return this.charity.volunteersApplied === this.charity.volunteersRequired;
    }

    requiresVolunteers(): boolean {
        return this.charity.volunteersRequired > 0;
    }

    requiresDonations(): boolean {
        return this.charity.amountRequired > 0;
    }

    loadData(): void {
        this.loadCharity();
        this.loadUserVolunteering();
    }

    loadCharity(): void {
        this.charityService.getCharityById(this.id)
            .subscribe(charity => {
                this.initializeCharity(charity);
            }, () => {
                this.navigateToNotFound();
            });
    }

    loadUserVolunteering(): void {
        this.volunteerService.getUserVolunteering(this.id)
            .subscribe(
                () => {
                    this.userHasVolunteered = true;
                },
                (error: HttpErrorResponse) => {
                    if (error.status === 404) {
                        this.userHasVolunteered = false;
                    }
                });
    }

    showFailure(failedAction: string) {
        this.actionFailed = true;
        this.failedAction = failedAction;
    }
}
