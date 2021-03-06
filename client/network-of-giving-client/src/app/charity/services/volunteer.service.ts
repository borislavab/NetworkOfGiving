import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Volunteering } from '../models/volunteering.model';

@Injectable({
    providedIn: 'root'
})
export class VolunteerService {

    constructor(private http: HttpClient) { }

    volunteerToCharity(id: number): Observable<any> {
        return this.http.post<any>(`${environment.apiUrl}/charities/volunteer/${id}`, {});
    }

    getUserVolunteering(id: number): Observable<Volunteering> {
        return this.http.get<Volunteering>(`${environment.apiUrl}/charities/volunteer/${id}/me`);
    }
}
