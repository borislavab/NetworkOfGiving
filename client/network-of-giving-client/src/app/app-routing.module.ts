import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LoginComponent, RegisterComponent } from './authentication/authentication.module';
import { AnonymousGuard } from './authentication/guards/anonymous.guard';
import { AuthGuard } from './authentication/guards/auth.guard';
import { CreateCharityComponent } from './charity/create-charity/create-charity.component';
import { CharityListComponent } from './charity/charity-list/charity-list.component';

const routes: Routes = [
    {
        path: '', redirectTo: 'charities', pathMatch: 'full'
    },
    {
        path: 'login', component: LoginComponent, canActivate: [AnonymousGuard]
    },
    {
        path: 'register', component: RegisterComponent, canActivate: [AnonymousGuard]
    },
    {
        path: 'charities/create', component: CreateCharityComponent, canActivate: [AuthGuard]
    },
    {
        path: 'charities', component: CharityListComponent
    },
    {
        path: '**', redirectTo: '/'
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }
