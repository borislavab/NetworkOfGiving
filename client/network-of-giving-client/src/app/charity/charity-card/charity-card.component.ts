import { Component, OnInit, Input } from '@angular/core';
import { Charity } from '../models/charity.model';

@Component({
    selector: 'app-charity-card',
    templateUrl: './charity-card.component.html',
    styleUrls: ['./charity-card.component.scss']
})
export class CharityCardComponent implements OnInit {

    @Input()
    charity: Charity;

    constructor() { }

    ngOnInit(): void {
        if (!this.charity.thumbnail) {
            this.charity.thumbnail = 'assets/missing-image.png';
        }
    }
}
