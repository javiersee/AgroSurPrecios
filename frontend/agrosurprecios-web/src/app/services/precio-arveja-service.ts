import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PrecioArveja } from '../models/precio-arveja.model';

@Injectable({
  providedIn: 'root',
})
export class PrecioArvejaService  {

  private apiUrl = 'http://localhost:8080/api/precios';
  
  constructor(private http: HttpClient) {}

  obtenerHistorico(): Observable<PrecioArveja[]> {
    return this.http.get<PrecioArveja[]>(this.apiUrl);
  }
  obtenerPromedioTotal(): Observable<number> {
  return this.http.get<number>(`${this.apiUrl}/promedioTotal`);
  }
}
