import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PrecioArveja } from '../../models/precio-arveja.model';
import { precioService } from '../../services/precioService';

@Component({
  selector: 'app-ultimo-precio',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './ultimo-precio.html', // Asegúrate de que el nombre coincida
  styleUrl: './ultimo-precio.css',
})
export class UltimoPrecio implements OnInit {
  private service = inject(precioService);
  private cdr = inject(ChangeDetectorRef);
  public dato?: PrecioArveja;

  ngOnInit(): void {
    console.log('--- Iniciando petición al Backend (8080) ---');
    this.service.obtenerUltimo().subscribe({
      next: (respuesta) => {
        console.log('¡Datos recibidos del servidor!', respuesta);
        this.dato = respuesta;
        this.cdr.detectChanges(); // <--- Fuerza a Angular a pintar los datos de Ipiales
      },
      error: (error) => {
        console.error('ERROR CRÍTICO:', error);
      }
    });
  }
}