import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LoginComponent, RegisterComponent } from './authentication/authentication.module';
import { HomeComponent } from './components/home/home.component';
import { AnonymousGuard } from './authentication/guards/anonymous.guard';

const routes: Routes = [
    {
        path: '', component: HomeComponent
    },
    {
        path: 'login', component: LoginComponent, canActivate: [AnonymousGuard]
    },
    {
        path: 'register', component: RegisterComponent, canActivate: [AnonymousGuard]
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
