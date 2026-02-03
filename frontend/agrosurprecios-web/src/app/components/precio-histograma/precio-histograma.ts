import { Component, OnInit } from '@angular/core';
import { PrecioArvejaService } from '../../services/precio-arveja-service';
import { PrecioArveja } from '../../models/precio-arveja.model';
import Chart from 'chart.js/auto';

@Component({
  selector: 'app-precio-histograma',
  imports: [],
  templateUrl: './precio-histograma.html',
  styleUrl: './precio-histograma.css',
})
export class PrecioHistograma implements OnInit {

  precios: PrecioArveja[] = [];
  chart!: Chart;
  periodo: string = 'Hoy';

  constructor(private precioService: PrecioArvejaService) {}

  ngOnInit(): void {
    this.obtenerPrecios();
    this.periodo = "Hoy";
  }
  onPeriodoChange(event: any) {
  //this.periodo = event.target.value;
  //this.cargarDatos();
 }

  obtenerPrecios() {
    this.precioService.obtenerHistorico().subscribe({
      next: data => {

        // ✅ Ordenar por fecha ascendente
        this.precios = data.sort(
          (a, b) => new Date(a.fecha).getTime() - new Date(b.fecha).getTime()
        );

        this.crearGraficoLinea(); // 🔥 CAMBIO NOMBRE FUNCIÓN
      },
      error: err => console.error('Error al obtener precios', err)
    });
  }

  crearGraficoLinea() {

    // ✅ Fecha + Hora (Trading necesita tiempo completo)
    const labels = this.precios.map(p =>
      new Date(p.fecha).toLocaleString()
    );

    const valores = this.precios.map(p => p.precioBulto);

    if (this.chart) {
      this.chart.destroy();
    }

    this.chart = new Chart('histogramaPrecios', {
      type: 'line', // 🔥 CAMBIO CLAVE (ANTES era bar)

      data: {
        labels,
        datasets: [{
          label: 'Precio por bulto (COP)',
          data: valores,
          borderWidth: 2,
          tension: 0.3,     // curva suave tipo trading
          pointRadius: 2,   // puntos pequeños
          fill: false       // sin relleno
        }]
      },

      options: {
        responsive: true,
        plugins: {
          legend: {
            display: true
          }
        },
        scales: {
          x: {
            title: {
              display: true,
              text: 'Fecha y Hora'
            }
          },
          y: {
            title: {
              display: true,
              text: 'Precio (COP)'
            }
          }
        }
      }
    });
  }
}
