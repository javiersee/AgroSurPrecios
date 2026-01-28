import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PrecioArveja } from '../models/precio-arveja.model';
@Injectable({
  providedIn: 'root',
})
export class precioService {
   private apiUrl = 'http://localhost:8080/api/precios/ultimo';
  
  constructor(private http: HttpClient) {}

  obtenerUltimo(): Observable<PrecioArveja> {
    return this.http.get<PrecioArveja>(this.apiUrl);
  }
  
}
