import { Component, OnInit  } from '@angular/core';
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
  constructor(private precioService: PrecioArvejaService) {}

   ngOnInit(): void {
    this.obtenerPrecios();
  }
  obtenerPrecios() {
    this.precioService.obtenerHistorico().subscribe({
      next: data => {
        // ordenar por fecha ascendente
        this.precios = data.sort(
          (a, b) => new Date(a.fecha).getTime() - new Date(b.fecha).getTime()
        );

        this.crearHistograma();
      },
      error: err => console.error('Error al obtener precios', err)
    });
  }
  crearHistograma() {
    const labels = this.precios.map(p =>
      new Date(p.fecha).toLocaleDateString()
    );

    const valores = this.precios.map(p => p.precioBulto);

    if (this.chart) {
      this.chart.destroy();
    }

    this.chart = new Chart('histogramaPrecios', {
      type: 'bar',
      data: {
        labels,
        datasets: [{
          label: 'Precio por bulto (COP)',
          data: valores
        }]
      },
      options: {
        responsive: true
      }
    });
  }
}
