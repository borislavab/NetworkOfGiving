import { Component, OnInit } from '@angular/core';

import { CharityService } from '../services/charity.service';
import { Charity } from '../models/charity.model';

@Component({
    selector: 'app-charity-list',
    templateUrl: './charity-list.component.html',
    styleUrls: ['./charity-list.component.scss']
})
export class CharityListComponent implements OnInit {

    charityList: Charity[];

    constructor(private charityService: CharityService) { }

    ngOnInit(): void {
        this.charityService.getAllCharities()
            .subscribe(charityList => {
                this.charityList = charityList;
            });
    }

}
