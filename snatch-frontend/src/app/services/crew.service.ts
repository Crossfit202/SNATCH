import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Crew } from '../models/Crew';

@Injectable({
  providedIn: 'root'
})
export class CrewService {

  private apiURL = '/api/crew'

  constructor(private http: HttpClient) { }

  getAllCrews(): Observable<Crew[]> {
    return this.http.get<Crew[]>(this.apiURL);
  }

  addCrew(crew: Crew): Observable<Crew> {
    return this.http.post<Crew>(this.apiURL, crew);
  }

  updateCrew(id: number, crew: Crew): Observable<Crew> {
    return this.http.put<Crew>(`${this.apiURL}/${id}`, crew);
  }



  deleteCrew(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiURL}/${id}`);
  }
}
