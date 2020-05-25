import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../services/authentication.service';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

    form: FormGroup = new FormGroup({
        username: new FormControl('', Validators.required),
        password: new FormControl('', Validators.required)
    });

    invalidCredentials: boolean;
    failedLogin: boolean;

    constructor(private authService: AuthenticationService,
                private router: Router) { }

    ngOnInit(): void {
    }

    login(): void {
        const { username, password } = this.form.value;
        this.authService.login(username, password).subscribe(
            () => this.redirectToHome(),
            error => this.handleError(error));
    }

    redirectToHome() {
        this.router.navigate(['/']);
    }

    handleError(error) {
        this.failedLogin = true;
        if (error.status === 400) {
            this.invalidCredentials = true;
        } else {
            this.invalidCredentials = false;
        }
    }
}
