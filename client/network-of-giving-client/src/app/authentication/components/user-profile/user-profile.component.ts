import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../services/authentication.service';
import { UserInformation } from '../../models/user-information.model';

@Component({
    selector: 'app-user-profile',
    templateUrl: './user-profile.component.html',
    styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {

    userInformation: UserInformation;

    constructor(private authService: AuthenticationService) {
        this.authService.getUserInformation().subscribe(
            userInfo => {
                this.userInformation = userInfo;
            }
        );
    }

    ngOnInit(): void {
    }

}
