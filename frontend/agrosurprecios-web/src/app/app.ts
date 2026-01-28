import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { UltimoPrecio } from './components/ultimo-precio/ultimo-precio';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, UltimoPrecio],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('agrosurprecios-web');
}
