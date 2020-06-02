import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, Validators, FormControl, ValidatorFn, ValidationErrors } from '@angular/forms';
import { CharityCreationModel } from '../../models/charity-creation.model';

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
    selector: 'app-charity-form',
    templateUrl: './charity-form.component.html',
    styleUrls: ['./charity-form.component.scss']
})
export class CharityFormComponent implements OnInit {

    @Input()
    defaultValues: CharityCreationModel;

    @Input()
    submitActionName: string;

    @Output()
    submittedCharity: EventEmitter<CharityCreationModel> = new EventEmitter();

    form: FormGroup;
    thumbnail: string = null;

    readonly minTitleLength = 5;
    readonly maxTitleLength = 50;
    readonly maxDescriptionLength = 255;

    constructor() { }

    ngOnInit(): void {
        this.form = new FormGroup({
            title: new FormControl(this.defaultValues.title, [
                Validators.required,
                Validators.minLength(this.minTitleLength),
                Validators.maxLength(this.maxTitleLength)
            ]),
            description: new FormControl(this.defaultValues.description, [
                Validators.required,
                Validators.maxLength(this.maxDescriptionLength)
            ]),
            thumbnail: new FormControl(),
            amountRequired: new FormControl(this.defaultValues.amountRequired, [Validators.min(0.0), Validators.required]),
            volunteersRequired: new FormControl(this.defaultValues.volunteersRequired, [Validators.min(0), Validators.required])
        }, {validators: requiresResourcesValidator('amountRequired', 'volunteersRequired')});

        this.thumbnail = this.defaultValues.thumbnail;
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

    sumbit() {
        this.submittedCharity.next({
            ...this.form.value,
            thumbnail: this.thumbnail
        });
    }

    noResourcesRequired(): boolean {
        return this.form.errors?.resourcesRequired &&
            this.form.controls.volunteersRequired.touched;
    }
}
