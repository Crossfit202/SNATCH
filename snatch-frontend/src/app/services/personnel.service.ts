import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Personnel } from '../models/Personnel';

@Injectable({
  providedIn: 'root' // Ensures this service is available application-wide
})
export class PersonnelService {

  // Backend API endpoint for personnel-related operations
  private apiURL = '/api/personnel';

  // Injects HttpClient to allow communication with the backend
  constructor(private http: HttpClient) { }

  // ==============================
  // CRUD OPERATIONS FOR PERSONNEL
  // ==============================

  /**
   * Fetches all personnel from the backend.
   * @returns An Observable containing an array of Personnel objects.
   */
  getAllPersonnels(): Observable<Personnel[]> {
    return this.http.get<Personnel[]>(this.apiURL);
  }

  /**
   * Adds a new personnel entry to the backend.
   * @param personnel - The personnel object to be added.
   * @returns An Observable containing the created Personnel object.
   */
  addPersonnel(personnel: Personnel): Observable<Personnel> {
    return this.http.post<Personnel>(this.apiURL, personnel);
  }

  /**
   * Updates an existing personnel entry in the backend.
   * @param id - The ID of the personnel to be updated.
   * @param personnel - The updated personnel data.
   * @returns An Observable containing the updated Personnel object.
   */
  updatePersonnel(id: number, personnel: Personnel): Observable<Personnel> {
    return this.http.put<Personnel>(`${this.apiURL}/${id}`, personnel);
  }

  /**
   * Deletes a personnel entry from the backend.
   * @param id - The ID of the personnel to be deleted.
   * @returns An Observable that completes once the delete operation is successful.
   */
  deletePersonnel(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiURL}/${id}`);
  }

}
