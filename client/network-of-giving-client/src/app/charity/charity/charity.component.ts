import { Component, OnInit, Input } from '@angular/core';
import { Charity } from '../models/charity.model';

@Component({
    selector: 'app-charity',
    templateUrl: './charity.component.html',
    styleUrls: ['./charity.component.scss']
})
export class CharityComponent implements OnInit {

    @Input()
    charity: Charity;

    constructor() { }

    ngOnInit(): void { }
}
