import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { CharityCreationModel } from './models/charity-creation.model';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class CharityService {

    constructor(private http: HttpClient) {}

    createCharity(charityParameters: CharityCreationModel): Observable<any> {
        console.log(charityParameters);
        return this.http.post<any>(`${environment.apiUrl}/charities`, charityParameters);
    }
}
