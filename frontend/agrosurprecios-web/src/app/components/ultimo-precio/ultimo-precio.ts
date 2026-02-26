import { Component, OnInit, OnDestroy, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PrecioArveja } from '../../models/precio-arveja.model';
import { precioService } from '../../services/precioService';
import { Subscription, interval } from 'rxjs';

@Component({
  selector: 'app-ultimo-precio',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './ultimo-precio.html', // Asegúrate de que el nombre coincida
  styleUrl: './ultimo-precio.css',
})
export class UltimoPrecio implements OnInit, OnDestroy {
  private service = inject(precioService);
  private cdr = inject(ChangeDetectorRef);
  public dato?: PrecioArveja;
  private pollSub?: Subscription;

  ngOnInit(): void {
    this.cargarUltimo();

    // Polling cada 5 segundos: si cambia el id o la fecha, recargamos la página
    this.pollSub = interval(5000).subscribe(() => {
      this.service.obtenerUltimo().subscribe({
        next: (nuevo) => {
          if (this.dato && nuevo && (nuevo.id !== this.dato.id || nuevo.fecha !== this.dato.fecha)) {
            window.location.reload();
          }
        },
        error: (error) => {
          console.error('Error al refrescar último precio:', error);
        }
      });
    });
  }

  ngOnDestroy(): void {
    this.pollSub?.unsubscribe();
  }

  private cargarUltimo(): void {
    this.service.obtenerUltimo().subscribe({
      next: (respuesta) => {
        this.dato = respuesta;
        this.cdr.detectChanges();
      },
      error: (error) => {
        console.error('ERROR CRÍTICO:', error);
      }
    });
  }
}