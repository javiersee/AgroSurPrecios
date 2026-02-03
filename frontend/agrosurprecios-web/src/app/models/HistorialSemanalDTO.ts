
import { SemanaPromedio } from './SemanaPromedio';
export interface HistorialSemanalDTO {
  semanas: SemanaPromedio[];
  promedioGeneralSemanas: number | null; // O number | undefined
}