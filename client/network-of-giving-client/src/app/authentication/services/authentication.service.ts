import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

import { environment } from 'src/environments/environment';
import { TokenService } from './token.service';
import { TokenResponse } from '../models/token-response.model';
import { AuthenticatedUser } from '../models/authenticated-user.model';
import { RegistrationData } from '../models/registration-data.model';
import { UserInformation } from '../models/user-information.model';


@Injectable({
    providedIn: 'root'
})
export class AuthenticationService {
    private currentUserSubject: BehaviorSubject<AuthenticatedUser>;
    public $currentUser: Observable<AuthenticatedUser>;

    constructor(private http: HttpClient,
                private tokenService: TokenService) {
        const authenticatedUser = this.getUserInfoFromTokenData();
        this.currentUserSubject = new BehaviorSubject<AuthenticatedUser>(authenticatedUser);
        this.$currentUser = this.currentUserSubject.asObservable();
    }

    public get currentUser(): AuthenticatedUser {
        return this.currentUserSubject.value;
    }

    login(username: string, password: string): Observable<TokenResponse> {
        return this.http.post<TokenResponse>(`${environment.apiUrl}/users/login`, { username, password })
            .pipe(tap(tokenResponse => this.processTokenResponse(tokenResponse)));
    }

    logout() {
        this.tokenService.clearToken();
        this.currentUserSubject.next(null);
    }

    register(registrationData: RegistrationData): Observable<any> {
        return this.http.post<any>(`${environment.apiUrl}/users/register`, registrationData);
    }

    getUserInformation(): Observable<UserInformation> {
        return this.http.get<UserInformation>(`${environment.apiUrl}/users/me`);
    }

    isAuthenticated() {
        if (this.tokenService.hasValidToken()) {
            return true;
        }
        if (this.currentUser) {
            this.currentUserSubject.next(null);
        }
        return false;
    }

    private processTokenResponse(tokenResponse: TokenResponse) {
        this.tokenService.saveTokenResponse(tokenResponse);
        const authenticatedUser = this.getUserInfoFromTokenData();
        this.currentUserSubject.next(authenticatedUser);
    }

    private getUserInfoFromTokenData(): AuthenticatedUser {
        const tokenData = this.tokenService.data;
        if (tokenData == null) {
            return null;
        }
        return {
            id: Number(tokenData.sub),
            username: tokenData.username
        };
    }
}
