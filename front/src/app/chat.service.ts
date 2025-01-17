import { Injectable } from '@angular/core';
import * as Stomp from 'stompjs';
import { BehaviorSubject, Subject } from 'rxjs';
import SockJS from 'sockjs-client';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private stompClient!: Stomp.Client;
  private messageSubject = new Subject<any>();
  private isConnectedSubject = new BehaviorSubject<boolean>(false);
  private API_URL = 'http://localhost:8080/api/mensajes';

  constructor(private http: HttpClient) {}

  conectar() {
    if (this.isConnectedSubject.value) {
      console.log('Ya estás conectado');
      return;
    }

    const socket = new SockJS('http://localhost:8080/chat-websocket');
    this.stompClient = Stomp.over(socket);

    this.stompClient.connect({}, (frame) => {
      console.log('Conectado: ' + frame);
      this.isConnectedSubject.next(true);

      const username = localStorage.getItem('username') || 'SinNombre';
      console.log('Suscribiéndose a /topic/privado/' + username);
      this.stompClient.subscribe('/topic/privado/' + username, (message) => {
          console.log('Mensaje recibido:', message.body);
          this.messageSubject.next(JSON.parse(message.body));
      });
      
    }, (error) => {
      console.error('Error al conectar:', error);
    });
  }

  desconectar() {
    if (this.stompClient && this.isConnectedSubject.value) {
      this.stompClient.disconnect(() => {
        console.log('Desconectado del servidor');
        this.isConnectedSubject.next(false);
      });
    } else {
      console.log('No estás conectado');
    }
  }

  // Enviar SIEMPRE a la ruta privada
  sendMessage(mensaje: any) {
    if (!this.isConnectedSubject.value) {
      console.error('No se puede enviar el mensaje. No estás conectado.');
      return;
    }

    console.log('Enviando mensaje privado:', mensaje);
    this.stompClient.send('/app/mensajePrivado', {}, JSON.stringify(mensaje));
  }

  // Escuchar mensajes entrantes
  getMessages() {
    return this.messageSubject.asObservable();
  }

  // HTTP
  getConversacion(emisor: string, destinatario: string) {
    return this.http.get<any[]>(
      `${this.API_URL}/conversacion/${emisor}/${destinatario}`
    );
  }
}
