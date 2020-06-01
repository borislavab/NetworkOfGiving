import { Component, OnInit } from '@angular/core';

import { CharityService } from '../../services/charity.service';
import { Charity } from '../../models/charity.model';
import { AuthenticationService } from 'src/app/authentication/authentication.module';
import { FormGroup, FormControl } from '@angular/forms';
import { debounceTime, switchMap } from 'rxjs/operators';

@Component({
    selector: 'app-charity-list',
    templateUrl: './charity-list.component.html',
    styleUrls: ['./charity-list.component.scss']
})
export class CharityListComponent implements OnInit {

    charityList: Charity[];

    searchForm: FormGroup;

    constructor(private charityService: CharityService,
                private authService: AuthenticationService) {
        this.searchForm = new FormGroup({
            searchTerm: new FormControl()
        });

        this.searchForm.controls.searchTerm.valueChanges.pipe(
            debounceTime(200),
            switchMap(searchTerm => this.charityService.getAllCharities(searchTerm)))
                .subscribe(charityList => {
                    this.charityList = charityList;
                });
    }

    ngOnInit(): void {
        this.charityService.getAllCharities()
            .subscribe(charityList => {
                this.charityList = charityList;
            });
    }

    userIsAuthenticated(): boolean {
        return this.authService.isAuthenticated();
    }

}
