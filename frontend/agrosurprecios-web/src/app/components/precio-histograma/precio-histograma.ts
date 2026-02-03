import { Component, OnInit } from '@angular/core';
import { PrecioArvejaService } from '../../services/precio-arveja-service';
import { PrecioArveja } from '../../models/precio-arveja.model';
import Chart from 'chart.js/auto';
import { CommonModule } from '@angular/common';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-precio-histograma',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './precio-histograma.html',
  styleUrl: './precio-histograma.css',
})
export class PrecioHistograma implements OnInit {
  precios: PrecioArveja[] = [];
  chart!: Chart;
  // ✅ Cambiado a 'todos' para coincidir con el valor del select
  periodo: string = 'todos'; 
  promedioTotal$!: Observable<number>;

  constructor(private precioService: PrecioArvejaService) {}

  ngOnInit(): void {
    this.promedioTotal$ = this.precioService.obtenerPromedioTotal();
    this.obtenerPrecios(); // Al cargar por defecto trae el histórico
  }

  onPeriodoChange(event: any) {
    this.periodo = event.target.value;
    // Aquí podrías añadir lógica para filtrar localmente o llamar a otro endpoint
  }

  obtenerPrecios() {
    this.precioService.obtenerHistorico().subscribe({
      next: data => {
        this.precios = data.sort(
          (a, b) => new Date(a.fecha).getTime() - new Date(b.fecha).getTime()
        );
        this.crearGraficoLinea();
      },
      error: err => console.error('Error al obtener precios', err)
    });
  }

  crearGraficoLinea() {
    const labels = this.precios.map(p => new Date(p.fecha).toLocaleString());
    const valores = this.precios.map(p => p.precioBulto);

    if (this.chart) {
      this.chart.destroy();
    }

    this.chart = new Chart('histogramaPrecios', {
      type: 'line',
      data: {
        labels,
        datasets: [{
          label: 'Precio por bulto (COP)',
          data: valores,
          // ✅ Color verde institucional aplicado
          borderColor: '#689f38', 
          backgroundColor: 'rgba(104, 159, 56, 0.1)',
          borderWidth: 3,
          tension: 0.4,
          pointRadius: 3,
          pointBackgroundColor: '#689f38',
          fill: true
        }]
      },
      options: {
        responsive: true,
        plugins: { legend: { display: true } },
        scales: {
          x: { title: { display: true, text: 'Fecha y Hora' } },
          y: { title: { display: true, text: 'Precio (COP)' } }
        }
      }
    });
  }
}