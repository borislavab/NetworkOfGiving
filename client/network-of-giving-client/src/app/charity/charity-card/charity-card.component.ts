import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';

import { Charity } from '../models/charity.model';

@Component({
    selector: 'app-charity-card',
    templateUrl: './charity-card.component.html',
    styleUrls: ['./charity-card.component.scss']
})
export class CharityCardComponent implements OnInit {

    @Input()
    charity: Charity;

    constructor(private router: Router) { }

    ngOnInit(): void {
        if (!this.charity.thumbnail) {
            this.charity.thumbnail = 'assets/missing-image.png';
        }
    }

    navigateToCharityPage() {
        this.router.navigate(['/charities', this.charity.id]);
    }
}
