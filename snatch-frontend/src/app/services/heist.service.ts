import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Heist } from '../models/Heist';

@Injectable({
    providedIn: 'root'
})
export class HeistService {
    private apiUrl = '/api/heist'; // Update with your backend URL

    constructor(private http: HttpClient) { }

    // Get all heists
    getHeists(): Observable<Heist[]> {
        return this.http.get<Heist[]>(this.apiUrl);
    }

    // Add a new heist
    addHeist(heist: Heist): Observable<Heist> {
        return this.http.post<Heist>(this.apiUrl, heist);
    }

    // Update an existing heist
    updateHeist(heist: Heist): Observable<Heist> {
        return this.http.put<Heist>(`${this.apiUrl}/${heist.heistId}`, heist);
    }

    // Delete a heist
    deleteHeist(heistId: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${heistId}`);
    }
}
