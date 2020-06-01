import { Component, OnInit, Input, Output, EventEmitter, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { Charity } from '../models/charity.model';
import { FormControl, Validators, FormGroup } from '@angular/forms';

@Component({
    selector: 'app-donate',
    templateUrl: './donate.component.html',
    styleUrls: ['./donate.component.scss']
})
export class DonateComponent implements OnInit, AfterViewInit {

    @Input()
    charity: Charity;

    @Output()
    donationProcessed: EventEmitter<boolean> = new EventEmitter();

    shouldOpen = true;

    @ViewChild('donationInput', { static: false }) donationInput: ElementRef;

    form: FormGroup;

    constructor() { }

    ngOnInit(): void {
        this.form = new FormGroup({
            donationAmount: new FormControl(0.01, [
                Validators.required,
                Validators.min(0.01),
                Validators.max(this.charity.amountRequired - this.charity.amountCollected)])
            });
    }

    ngAfterViewInit(): void {
        setTimeout(() => this.donationInput.nativeElement.focus());
    }

    donate() {
        console.log('Donate', this.form.value.donationAmount);
        this.donationProcessed.emit(true);
    }

    closeModal() {
        console.log('Closing');
        this.donationProcessed.emit(false);
    }

    stopPropagation(event: Event) {
        event.stopPropagation();
    }
}
