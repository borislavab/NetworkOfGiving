import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LoginComponent, RegisterComponent } from './authentication/authentication.module';
import { AnonymousGuard } from './authentication/guards/anonymous.guard';
import { AuthGuard } from './authentication/guards/auth.guard';
import { CreateCharityComponent } from './charity/components/create-charity/create-charity.component';
import { CharityListComponent } from './charity/components/charity-list/charity-list.component';
import { CharityComponent } from './charity/components/charity/charity.component';
import { EditCharityComponent } from './charity/components/edit-charity/edit-charity.component';

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
        path: 'charities/:id', component: CharityComponent
    },
    {
        path: 'charities/edit/:id', component: EditCharityComponent
    },
    {
        path: '**', redirectTo: 'charities'
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }
