import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { DonationAmount } from '../models/donation-amount.model';

@Injectable({
    providedIn: 'root'
})
export class DonateService {

    constructor(private http: HttpClient) { }

    donateToCharity(id: number, amount: number): Observable<any> {
        const body: DonationAmount = {amount};
        return this.http.post<any>(`${environment.apiUrl}/charities/donate/${id}`, body);
    }
}
