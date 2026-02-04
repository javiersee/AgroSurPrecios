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
  periodo: string = 'todos'; 
  promedioTotal$!: Observable<number>;
  
  // Variable para guardar el promedio dinámico del periodo seleccionado
  promedioVisual: number = 0;

  constructor(private precioService: PrecioArvejaService) {}

  ngOnInit(): void {
    this.promedioTotal$ = this.precioService.obtenerPromedioTotal();
    this.obtenerPrecios(); // Carga inicial (Todos)
  }

  // ✅ El cerebro del componente: detecta el cambio en el select
  onPeriodoChange(event: any) {
    this.periodo = event.target.value;

    switch (this.periodo) {
      

      case 'hoy':
        this.precioService.obtenerHoy().subscribe(res => {
          // 1. Extraemos la hora y minutos (ej: 03:59 PM o 15:59 según el navegador)
          const labels = res.registros.map(r => {
            const fechaObj = new Date(r.fecha);
            return fechaObj.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
          });

          // 2. Extraemos los precios
          const valores = res.registros.map(r => r.precioBulto);

          // 3. Asignamos el promedio con el parche para el error de tipado
          this.promedioVisual = res.promedio || 0;

          // 4. Actualizamos el gráfico con un título descriptivo
          this.actualizarGrafico(labels, valores, 'Precios Detallados de Hoy');
        });
        break;

      case 'semana':
        this.precioService.obtenerSemanal().subscribe(res => {
          const labels = res.semanas.map(s => s.mercado);
          const valores = res.semanas.map(s => s.promedio);
          this.promedioVisual = res.promedioGeneralSemanas || 0;
          this.actualizarGrafico(labels, valores, 'Promedio Semanal');
        });
        break;

      case 'mes':
          this.precioService.obtenerMensual().subscribe(res => {
            // 1. Usamos 'semanas' porque así viene en tu JSON (aunque sean meses)
            const labels = res.semanas.map(m => m.mercado); 
            const valores = res.semanas.map(m => m.promedio);
            
            // 2. Usamos 'promedioGeneralSemanas' y agregamos el || 0 para el error de tipado
            this.promedioVisual = res.promedioGeneralSemanas || 0; 
            
            this.actualizarGrafico(labels, valores, 'Promedio Mensual');
          });
      break;

      case 'anio':
        this.precioService.obtenerAnual().subscribe(res => {
          // 1. Usamos 'semanas' porque así viene la lista en tu JSON
          // 2. Usamos 's.mercado' porque ahí es donde el back guardó el texto "2026"
          const labels = res.semanas.map(s => s.mercado); 
          const valores = res.semanas.map(s => s.promedio);
          
          // 3. Usamos 'promedioGeneralSemanas' y el || 0 para el error de tipado
          this.promedioVisual = res.promedioGeneralSemanas || 0; 
          
          this.actualizarGrafico(labels, valores, 'Promedio Anual');
        });
        break;

      default:
        this.obtenerPrecios(); // "Todos"
        break;
    }
  }

  // Mantiene tu lógica original para traer todo el histórico
  obtenerPrecios() {
    this.precioService.obtenerHistorico().subscribe({
      next: data => {
        const labels = data.map(p => new Date(p.fecha).toLocaleDateString());
        const valores = data.map(p => p.precioBulto);
        this.actualizarGrafico(labels, valores, 'Histórico Completo');
      },
      error: err => console.error('Error al obtener precios', err)
    });
  }

  // ✅ He unificado la creación del gráfico para que siempre use tu estilo verde
  actualizarGrafico(labels: string[], valores: number[], titulo: string) {
    if (this.chart) {
      this.chart.destroy();
    }

    this.chart = new Chart('histogramaPrecios', {
      type: 'line',
      data: {
        labels,
        datasets: [{
          label: titulo,
          data: valores,
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
          x: { title: { display: true, text: 'Tiempo' } },
          y: { title: { display: true, text: 'Precio (COP)' } }
        }
      }
    });
  }
}