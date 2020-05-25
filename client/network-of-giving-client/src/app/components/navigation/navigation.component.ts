import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { AuthenticationService } from 'src/app/authentication/authentication.module';
import { AuthenticatedUser } from 'src/app/authentication/models/authenticated-user.model';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.scss']
})
export class NavigationComponent implements OnInit {

  currentUser: AuthenticatedUser;
  logoutClicked = false;

  constructor(private authService: AuthenticationService,
              private router: Router) { }

  ngOnInit(): void {
    this.authService.$currentUser.subscribe(
      (currentUser: AuthenticatedUser) => {
        this.currentUser = currentUser;
      }
    );
  }

  onLogoutClicked() {
    this.logoutClicked = true; // opens confirmation dialog
  }

  // called upon confirming logout
  logout(): void {
    this.logoutClicked = false;
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
