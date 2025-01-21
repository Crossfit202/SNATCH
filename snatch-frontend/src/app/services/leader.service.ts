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

  getAllLeaders(): Observable<Leader[]> {
    return this.http.get<Leader[]>(this.apiUrl)
  }


}
