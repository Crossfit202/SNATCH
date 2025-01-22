import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Captain } from '../models/Captain';
import { Heist } from '../models/Heist';

@Injectable({
    providedIn: 'root'
})
export class HeistService {
    private apiUrl = '/api/heist';

    constructor(private http: HttpClient) { }

    getAllHeists(): Observable<Heist[]> {
        return this.http.get<Heist[]>(this.apiUrl);
    }

    addHeist(heist: Heist): Observable<Heist> {
        return this.http.post<Heist>(this.apiUrl, heist);
    }

    deleteHeist(id: number): Observable<void> {
        return this.http.delete<void>(this.apiUrl);
    }
}