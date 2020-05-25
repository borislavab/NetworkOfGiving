import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, ValidatorFn, AbstractControl, ValidationErrors } from '@angular/forms';

import { Gender } from '../../models/gender.enum';
import { AuthenticationService } from '../../services/authentication.service';
import { Router } from '@angular/router';

function usernameNotTakenValidator(
    getTakenUsername: () => string
): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
        const currentUsername = control.value as string;
        const takenUsername = getTakenUsername();
        if (!!takenUsername && takenUsername === currentUsername) {
            return {
                usernameTaken: true
            };
        }
        return null;
    };
}

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

    readonly minimumAge = 1;

    form: FormGroup;
    takenUsername: string = null;
    registrationFailed = false;

    Gender = Gender;

    constructor(private authService: AuthenticationService,
        private router: Router) { }

    ngOnInit(): void {
        this.form = new FormGroup({
            username: new FormControl(undefined, [
                Validators.required,
                Validators.minLength(6),
                Validators.pattern('^[a-zA-Z](\\w)*$'),
                usernameNotTakenValidator(() => this.takenUsername)
            ]),
            password: new FormControl(undefined, [Validators.required, Validators.minLength(6)]),
            name: new FormControl(undefined, [Validators.required, Validators.pattern('^[a-zA-Z][a-zA-Z ]*$')]),
            age: new FormControl(undefined, [Validators.required, Validators.min(this.minimumAge)]),
            gender: new FormControl(),
            location: new FormControl()
        });
    }

    register(): void {
        this.authService.register(this.form.value)
            .subscribe(
                () => this.redirectToHome(),
                (error) => this.handleError(error));
    }

    redirectToHome(): void {
        this.router.navigate(['/']);
    }

    handleError(error): void {
        this.registrationFailed = true;
        if (error.status && error.status === 409) {
            this.takenUsername = this.form.value.username;
            this.form.controls.username.updateValueAndValidity();
        }
    }

    isUsernameTaken(): boolean {
        return !!this.takenUsername && this.form.value.username === this.takenUsername;
    }
}
