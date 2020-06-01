import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

import { CharityCreationModel } from '../models/charity-creation.model';
import { environment } from 'src/environments/environment';
import { Charity } from '../models/charity.model';
import { CharityDetails } from '../models/charity-details.model';

@Injectable({
    providedIn: 'root'
})
export class CharityService {

    constructor(private http: HttpClient) {}

    createCharity(charityParameters: CharityCreationModel): Observable<any> {
        return this.http.post<any>(`${environment.apiUrl}/charities`, charityParameters);
    }

    getCharityById(id: number): Observable<CharityDetails> {
        return this.http.get<CharityDetails>(`${environment.apiUrl}/charities/${id}`);
    }

    getAllCharities(searchTerm?: string): Observable<Charity[]> {
        let params = new HttpParams();
        if (searchTerm) {
            params = params.set('titleFilter', searchTerm);
        }
        return this.http.get<Charity[]>(`${environment.apiUrl}/charities`, {params});
    }

    deleteCharity(id: number): Observable<any> {
        return this.http.delete<any>(`${environment.apiUrl}/charities/${id}`);
    }
}
