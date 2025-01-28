import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Leader } from '../models/Leader';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LeaderService {

  private apiUrl = '/api/leader';

  constructor(private http: HttpClient) { }

  getLeaders(): Observable<Leader[]> {
    return this.http.get<Leader[]>(this.apiUrl);
  }

  addLeader(leader: Leader): Observable<Leader> {
    return this.http.post<Leader>(this.apiUrl, leader);
  }

  deleteLeader(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  updateLeader(leader: Leader): Observable<Leader> {
    return this.http.put<Leader>(`${this.apiUrl}/${leader.leaderId}`, leader);
  }
}
