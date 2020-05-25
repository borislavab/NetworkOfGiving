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

    get formValue() {
        return this.form.value;
    }

    register(): void {
        this.authService.register(this.formValue)
            .subscribe(
                () => this.onRegister(),
                (error) => this.handleError(error));
    }

    onRegister(): void {
        const username = this.formValue.username;
        const password = this.formValue.password;
        this.authService.login(username, password)
            .subscribe(
                () => this.router.navigate(['/']),
                () => this.router.navigate(['/login'])
            );
    }

    handleError(error): void {
        this.registrationFailed = true;
        if (error.status && error.status === 409) {
            this.takenUsername = this.formValue.username;
            this.form.controls.username.updateValueAndValidity();
        }
    }

    isUsernameTaken(): boolean {
        return !!this.takenUsername && this.formValue.username === this.takenUsername;
    }
}
