import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Heist } from '../models/Heist';

@Injectable({
    providedIn: 'root'
})
export class HeistService {
    private apiUrl = '/api/heist';

    constructor(private http: HttpClient) { }

    // Get all captains
    getAllHeist(): Observable<Heist[]> {
        return this.http.get<Heist[]>(this.apiUrl);
    }

    // Get a single heist by ID
    getHeistById(id: number): Observable<Heist> {
        return this.http.get<Heist>(`${this.apiUrl}/${id}`);
    }

    // Create a new heist
    createHeist(heist: Heist): Observable<Heist> {
        return this.http.post<Heist>(this.apiUrl, heist);
    }

    // Update an existing heist
    updateHeist(heist: Heist): Observable<Heist> {
        return this.http.put<Heist>(`${this.apiUrl}/${heist.heistId
            }`, heist);
    }

    // Delete a heist by ID
    deleteHeist(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }


}