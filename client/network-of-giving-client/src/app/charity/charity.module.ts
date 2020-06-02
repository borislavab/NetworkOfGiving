import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CreateCharityComponent } from './components/create-charity/create-charity.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ClarityModule } from '@clr/angular';
import { NgxCurrencyModule } from 'ngx-currency';
import { CharityCardComponent } from './components/charity-card/charity-card.component';
import { RouterModule } from '@angular/router';
import { CharityListComponent } from './components/charity-list/charity-list.component';
import { CharityComponent } from './components/charity/charity.component';
import { DonateComponent } from './components/donate/donate.component';
import { EditCharityComponent } from './components/edit-charity/edit-charity.component';
import { CharityFormComponent } from './components/charity-form/charity-form.component';

@NgModule({
    declarations: [
        CreateCharityComponent,
        CharityCardComponent,
        CharityListComponent,
        CharityComponent,
        DonateComponent,
        EditCharityComponent,
        CharityFormComponent
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
