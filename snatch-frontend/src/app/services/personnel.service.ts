import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Personnel } from '../models/Personnel';

@Injectable({
  providedIn: 'root'
})
export class PersonnelService {

  private apiURL = '/api/personnel'

  constructor(private http: HttpClient) { }

  getAllPersonnels(): Observable<Personnel[]> {
    return this.http.get<Personnel[]>(this.apiURL);
  }

  deletePersonnel(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiURL}/${id}`);
  }



}
