import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CreateCharityComponent } from './create-charity/create-charity.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ClarityModule } from '@clr/angular';
import { NgxCurrencyModule } from 'ngx-currency';

@NgModule({
    declarations: [CreateCharityComponent],
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        ClarityModule,
        NgxCurrencyModule
    ]
})
export class CharityModule { }
