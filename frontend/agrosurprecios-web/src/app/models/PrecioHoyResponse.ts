import { PrecioArveja } from '../models/precio-arveja.model';
export interface PrecioHoyResponse {
  registros: PrecioArveja[];
  promedio: number;
}