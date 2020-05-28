import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CharityService } from '../charity.service';
import { Charity } from '../models/charity.model';

@Component({
    selector: 'app-charity',
    templateUrl: './charity.component.html',
    styleUrls: ['./charity.component.scss']
})
export class CharityComponent implements OnInit {

    id: number;
    charity: Charity;

    constructor(private route: ActivatedRoute,
                private router: Router,
                private charityService: CharityService) {
        this.id = Number(route.snapshot.params.id);
        if (isNaN(this.id)) {
            this.router.navigate(['not-found']);
        }
        this.charityService.getCharityById(this.id)
            .subscribe(charity => {
                this.charity = charity;
            }, () => {
                this.router.navigate(['not-found']);
            });
    }

    ngOnInit(): void {
    }

}
