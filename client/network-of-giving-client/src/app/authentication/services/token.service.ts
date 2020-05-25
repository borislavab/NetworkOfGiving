import { Injectable } from '@angular/core';
import { TokenResponse } from '../models/token-response.model';
import { TokenData } from '../models/token-data.model';

@Injectable({
    providedIn: 'root'
})
export class TokenService {

    private tokenResponse: TokenResponse;
    private tokenData: TokenData;
    private JWT_KEY_NAME = 'jwt';

    constructor() {
        this.tokenResponse = JSON.parse(localStorage.getItem(this.JWT_KEY_NAME));
        if (this.tokenResponse) {
            this.parseTokenData();
        }
    }

    get data(): TokenData {
        return this.tokenData;
    }

    clearToken() {
        this.tokenResponse = null;
        this.tokenData = null;
        localStorage.removeItem(this.JWT_KEY_NAME);
    }

    saveTokenResponse(tokenResponse: TokenResponse) {
        this.tokenResponse = tokenResponse;
        localStorage.setItem(this.JWT_KEY_NAME, JSON.stringify(this.tokenResponse));
        this.parseTokenData();
    }

    parseTokenData() {
        const jwt = this.tokenResponse.access_token;
        const body = jwt.split('.')[1];
        const decodedBody = atob(body);
        const decodedBodyData = JSON.parse(decodedBody);
        this.tokenData = decodedBodyData;
    }

    hasValidToken(): boolean {
        return !!this.tokenResponse &&
            !!this.tokenResponse.access_token &&
            this.isTokenNonExpired();
    }

    get token() {
        return this.tokenResponse?.access_token;
    }

    get tokenType() {
        return this.tokenResponse?.token_type;
    }

    private isTokenNonExpired(): boolean {
        const expirationDate = new Date(0);
        const tokenExpirationSeconds = this.tokenData.exp;
        expirationDate.setUTCSeconds(tokenExpirationSeconds);
        const currentDate = new Date();
        return currentDate < expirationDate;
    }
}
