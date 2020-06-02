import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CharityEvent } from '../models/charity-event.model';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class EventService {

    constructor(private httpClient: HttpClient) { }

    getEventsForUser(): Observable<CharityEvent[]> {
        return this.httpClient.get<CharityEvent[]>(`${environment.apiUrl}/events/me`);
    }
}
