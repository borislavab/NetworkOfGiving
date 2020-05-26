import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { CharityService } from '../charity.service';
import { CharityCreationModel } from '../models/charity-creation.model';

@Component({
    selector: 'app-create-charity',
    templateUrl: './create-charity.component.html',
    styleUrls: ['./create-charity.component.scss']
})
export class CreateCharityComponent implements OnInit {

    form: FormGroup;
    creationFailed = false;
    thumbnailImageBuffer: Uint8Array = null;

    constructor(private charityService: CharityService) { }

    ngOnInit(): void {
        this.form = new FormGroup({
            title: new FormControl(undefined, Validators.required),
            description: new FormControl(undefined, Validators.required),
            thumbnail: new FormControl(),
            amountRequired: new FormControl(0.0, Validators.min(0.0)),
            volunteersRequired: new FormControl(0, Validators.min(0))
        });
    }

    createCharity() {
        const charityParameters: CharityCreationModel = {
            ...this.form.value,
            thumbnail: this.thumbnailImageBuffer
        };
        this.charityService.createCharity(charityParameters)
            .subscribe();
    }

    onFileInput(files: FileList) {
        const file: File = files.item(0);
        const reader = new FileReader();
        reader.onload = this.handleFileLoad.bind(this);
        reader.readAsArrayBuffer(file);
    }

    handleFileLoad(event) {
        const arrayBuffer = event.target.result;
        this.thumbnailImageBuffer = new Uint8Array(arrayBuffer);
    }

}
