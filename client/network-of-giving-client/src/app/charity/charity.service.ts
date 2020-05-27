import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { CharityCreationModel } from './models/charity-creation.model';
import { environment } from 'src/environments/environment';
import { Charity } from './models/charity.model';

@Injectable({
    providedIn: 'root'
})
export class CharityService {

    constructor(private http: HttpClient) {}

    createCharity(charityParameters: CharityCreationModel): Observable<any> {
        console.log(charityParameters);
        return this.http.post<any>(`${environment.apiUrl}/charities`, charityParameters);
    }

    getCharityById(id: number): Observable<Charity> {
        return this.http.get<Charity>(`${environment.apiUrl}/charities/${id}`);
    }

    getAllCharities(): Observable<Charity[]> {
        return this.http.get<Charity[]>(`${environment.apiUrl}/charities`);
    }
}
