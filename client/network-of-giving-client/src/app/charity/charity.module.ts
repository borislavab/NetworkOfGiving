import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CreateCharityComponent } from './create-charity/create-charity.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ClarityModule } from '@clr/angular';
import { NgxCurrencyModule } from 'ngx-currency';
import { CharityComponent } from './charity/charity.component';
import { RouterModule } from '@angular/router';
import { CharityListComponent } from './charity-list/charity-list.component';

@NgModule({
    declarations: [
        CreateCharityComponent,
        CharityComponent,
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
