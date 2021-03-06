import { BrowserModule } from '@angular/platform-browser';
import { NgModule, DEFAULT_CURRENCY_CODE } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { ClarityModule } from '@clr/angular';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgxCurrencyModule } from 'ngx-currency';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { AuthenticationModule } from './authentication/authentication.module';
import { NavigationComponent } from './components/navigation/navigation.component';
import { HomeComponent } from './components/home/home.component';
import { CharityModule } from './charity/charity.module';
import { customCurrencyMaskConfig } from './ngx-currency-configuration';
import { NotFoundComponent } from './components/not-found/not-found.component';

@NgModule({
    declarations: [
        AppComponent,
        NavigationComponent,
        HomeComponent,
        NotFoundComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        ClarityModule,
        BrowserAnimationsModule,
        HttpClientModule,
        AuthenticationModule,
        CharityModule,
        NgxCurrencyModule.forRoot(customCurrencyMaskConfig)
    ],
    providers: [
        { provide: DEFAULT_CURRENCY_CODE, useValue: 'USD' }
    ],
    bootstrap: [AppComponent]
})
export class AppModule { }
