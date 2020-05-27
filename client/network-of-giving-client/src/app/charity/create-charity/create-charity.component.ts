import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, ValidationErrors, ValidatorFn } from '@angular/forms';
import { Router } from '@angular/router';

import { CharityService } from '../charity.service';
import { CharityCreationModel } from '../models/charity-creation.model';

function requiresResourcesValidator(...resources: string[]): ValidatorFn {
    return (form: FormGroup): ValidationErrors | null => {
        const formValues = form.value;
        const noResourcesRequired = resources.every((resourceName) => {
            return !formValues[resourceName] || formValues[resourceName] === 0;
        });
        if (noResourcesRequired) {
            return {
                resourcesRequired: true
            };
        }
        return null;
    };
}

@Component({
    selector: 'app-create-charity',
    templateUrl: './create-charity.component.html',
    styleUrls: ['./create-charity.component.scss']
})
export class CreateCharityComponent implements OnInit {

    form: FormGroup;
    creationFailed = false;
    thumbnail: string = null;

    readonly minTitleLength = 5;
    readonly maxTitleLength = 50;

    constructor(private charityService: CharityService,
                private router: Router) { }

    ngOnInit(): void {
        this.form = new FormGroup({
            title: new FormControl(undefined, [Validators.required,
                Validators.minLength(this.minTitleLength),
                Validators.maxLength(this.maxTitleLength)]),
            description: new FormControl(undefined, Validators.required),
            thumbnail: new FormControl(),
            amountRequired: new FormControl(0.0, Validators.min(0.0)),
            volunteersRequired: new FormControl(0, Validators.min(0))
        }, {validators: requiresResourcesValidator('amountRequired', 'volunteersRequired')});
    }

    createCharity() {
        const charityParameters: CharityCreationModel = {
            ...this.form.value,
            thumbnail: this.thumbnail
        };
        this.charityService.createCharity(charityParameters)
            .subscribe(
                () => this.router.navigate(['']),
                () => this.creationFailed = true
            );
    }

    onFileInput(files: FileList) {
        const file: File = files.item(0);
        const reader = new FileReader();
        reader.onload = this.handleFileLoad.bind(this);
        reader.readAsDataURL(file);
    }

    handleFileLoad(event) {
        this.thumbnail = event.target.result;
    }

    noResourcesRequired(): boolean {
        return this.form.errors?.resourcesRequired &&
            this.form.controls.volunteersRequired.touched;
    }
}
