import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PrecioArveja } from '../models/precio-arveja.model';
import { PrecioHoyResponse } from '../models/PrecioHoyResponse';
import { HistorialSemanalDTO } from '../models/HistorialSemanalDTO';

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
  // Detalle de hoy
  obtenerHoy(): Observable<PrecioHoyResponse> {
    return this.http.get<PrecioHoyResponse>(`${this.apiUrl}/hoy`);
  }

  // Historial por semanas (Gráfico de barras)
  obtenerSemanal(): Observable<HistorialSemanalDTO> {
    return this.http.get<HistorialSemanalDTO>(`${this.apiUrl}/historialSemanal`);
  }

  // Historial por meses
  obtenerMensual(): Observable<HistorialSemanalDTO> {
    return this.http.get<HistorialSemanalDTO>(`${this.apiUrl}/mensual`);
  }

  // Historial por años
  obtenerAnual(): Observable<HistorialSemanalDTO> {
    return this.http.get<HistorialSemanalDTO>(`${this.apiUrl}/anual`);
  }

  
}
