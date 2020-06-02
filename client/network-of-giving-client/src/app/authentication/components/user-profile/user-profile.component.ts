import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../services/authentication.service';
import { UserInformation } from '../../models/user-information.model';
import { CharityEvent } from 'src/app/charity/models/charity-event.model';
import { EventService } from 'src/app/charity/services/event.service';

@Component({
    selector: 'app-user-profile',
    templateUrl: './user-profile.component.html',
    styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {

    userInformation: UserInformation;
    events: CharityEvent[];

    constructor(private authService: AuthenticationService,
                private eventService: EventService) {
        this.authService.getUserInformation().subscribe(
            userInfo => {
                this.userInformation = userInfo;
            }
        );
        this.eventService.getEventsForUser().subscribe(
            events => {
                this.events = events;
            });
    }

    ngOnInit(): void {
    }

}
