import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CreateCharityComponent } from './create-charity/create-charity.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ClarityModule } from '@clr/angular';
import { NgxCurrencyModule } from 'ngx-currency';
import { CharityCardComponent } from './charity-card/charity-card.component';
import { RouterModule } from '@angular/router';
import { CharityListComponent } from './charity-list/charity-list.component';

@NgModule({
    declarations: [
        CreateCharityComponent,
        CharityCardComponent,
        CharityListComponent
    ],
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        ClarityModule,
        NgxCurrencyModule,
        RouterModule
    ]
})
export class CharityModule { }
