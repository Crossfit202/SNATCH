import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Loot } from '../models/Loot';

@Injectable({
    providedIn: 'root'
})
export class LootService {
    private apiUrl = '/api/loot';

    constructor(private http: HttpClient) { }

    getAllLoot(): Observable<Loot[]> {
        return this.http.get<Loot[]>(this.apiUrl);
    }

    addLoot(loot: Loot): Observable<Loot> {
        if (loot.lootId && loot.lootId !== 0) {
            return this.http.put<Loot>(`${this.apiUrl}/${loot.lootId}`, loot);
        }
        return this.http.post<Loot>(this.apiUrl, loot);
    }


    deleteLoot(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }
}