import { Component, OnInit, Input, Output, EventEmitter, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { Charity } from '../../models/charity.model';
import { FormControl, Validators, FormGroup } from '@angular/forms';
import { DonateService } from '../../services/donate.service';

@Component({
    selector: 'app-donate',
    templateUrl: './donate.component.html',
    styleUrls: ['./donate.component.scss']
})
export class DonateComponent implements OnInit, AfterViewInit {

    @Input()
    charity: Charity;

    @Output()
    donationSuccess: EventEmitter<any> = new EventEmitter();

    @Output()
    donationFailure: EventEmitter<any> = new EventEmitter();

    @Output()
    donationCancelled: EventEmitter<any> = new EventEmitter();

    shouldOpen = true;

    @ViewChild('donationInput', { static: false }) donationInput: ElementRef;

    form: FormGroup;

    constructor(private donateService: DonateService) { }

    ngOnInit(): void {
        this.form = new FormGroup({
            donationAmount: new FormControl(0.01, [
                Validators.required,
                Validators.min(0.01),
                Validators.max(this.charity.amountRequired - this.charity.amountCollected)])
            });
    }

    ngAfterViewInit(): void {
        this.donationInput.nativeElement.focus();
    }

    donate() {
        const amount: number = Number(this.form.value.donationAmount);
        this.donateService.donateToCharity(this.charity.id, amount).subscribe(
            () => this.donationSuccess.emit(),
            () => this.donationFailure.emit()
        );
    }

    closeModal() {
        this.donationCancelled.emit();
    }

    stopPropagation(event: Event) {
        event.stopPropagation();
    }
}
