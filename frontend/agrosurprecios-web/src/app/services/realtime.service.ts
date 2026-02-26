import { Injectable } from '@angular/core';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

@Injectable({
  providedIn: 'root',
})
export class RealtimeService {
  private client: Client | null = null;
  private isConnected = false;

  connect(onNewData: () => void): void {
    if (this.isConnected) {
      return;
    }

    this.client = new Client({
      webSocketFactory: () => new SockJS('http://localhost:8080/ws-precios'),
      reconnectDelay: 5000,
    });

    this.client.onConnect = () => {
      this.isConnected = true;
      this.client?.subscribe('/topic/cambio-precios', () => {
        onNewData();
      });
    };

    this.client.onStompError = () => {
      this.isConnected = false;
    };

    this.client.onWebSocketClose = () => {
      this.isConnected = false;
    };

    this.client.activate();
  }
}

