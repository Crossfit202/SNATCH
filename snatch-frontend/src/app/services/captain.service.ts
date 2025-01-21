import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Captain } from '../models/Captain';

@Injectable({
  providedIn: 'root'
})
export class CaptainService {
  private apiUrl = '/api/captain'; // Adjust API URL as needed

  constructor(private http: HttpClient) { }

  // Get all captains
  getAllCaptains(): Observable<Captain[]> {
    return this.http.get<Captain[]>(this.apiUrl);
  }

  // Get a single captain by ID
  getCaptainById(id: number): Observable<Captain> {
    return this.http.get<Captain>(`${this.apiUrl}/${id}`);
  }

  // Create a new captain
  createCaptain(captain: Captain): Observable<Captain> {
    return this.http.post<Captain>(this.apiUrl, captain);
  }

  // Update an existing captain
  updateCaptain(id: number, captain: Captain): Observable<Captain> {
    return this.http.put<Captain>(`${this.apiUrl}/${id}`, captain);
  }

  // Delete a captain by ID
  deleteCaptain(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
