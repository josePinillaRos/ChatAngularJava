/**
 * ChatService
 * 
 * Servicio que gestiona la conexión al servidor de WebSocket, el envío y recepción de mensajes,
 * y la recuperación de historiales de conversaciones mediante solicitudes HTTP.
 * Este servicio utiliza Stomp y SockJS para manejar la comunicación en tiempo real.
 * 
 * Author: Jose Pinilla
 */

import { Injectable } from '@angular/core';
import * as Stomp from 'stompjs'; // Librería Stomp para manejar WebSocket
import { BehaviorSubject, Subject } from 'rxjs'; // Sujeto para manejar mensajes
import SockJS from 'sockjs-client'; // Cliente SockJS para compatibilidad con WebSocket
import { HttpClient } from '@angular/common/http'; // Cliente HTTP de Angular para solicitudes al backend

@Injectable({
  providedIn: 'root' // Hace que este servicio esté disponible globalmente en la aplicación
})
export class ChatService {
  private stompClient!: Stomp.Client; // Cliente Stomp para manejar la conexión WebSocket
  private messageSubject = new Subject<any>(); // Sujeto para transmitir mensajes recibidos
  private isConnectedSubject = new BehaviorSubject<boolean>(false); // Estado de conexión
  private API_URL = 'http://localhost:8080/api/mensajes'; // URL base para solicitudes HTTP relacionadas con mensajes

  constructor(private http: HttpClient) {}

  /**
   * Establece la conexión al servidor WebSocket.
   * Se suscribe a un canal privado basado en el nombre de usuario.
   */
  conectar() {
    if (this.isConnectedSubject.value) {
      console.log('Ya estás conectado');
      return;
    }

    const socket = new SockJS('http://localhost:8080/chat-websocket'); // Conexión mediante SockJS
    this.stompClient = Stomp.over(socket); // Inicializa el cliente Stomp

    this.stompClient.connect({}, (frame) => {
      console.log('Conectado: ' + frame); // Log de conexión exitosa
      this.isConnectedSubject.next(true); // Actualiza el estado de conexión

      // Recupera el nombre de usuario desde el almacenamiento local
      const username = localStorage.getItem('username') || 'SinNombre';
      console.log('Suscribiéndose a /topic/privado/' + username);

      // Suscribirse al canal privado del usuario
      this.stompClient.subscribe('/topic/privado/' + username, (message) => {
        console.log('Mensaje recibido:', message.body);
        this.messageSubject.next(JSON.parse(message.body)); // Transmite el mensaje recibido
      });
    }, (error) => {
      console.error('Error al conectar:', error); // Manejo de errores de conexión
    });
  }

  /**
   * Desconecta del servidor WebSocket.
   * Actualiza el estado de conexión y cierra la conexión Stomp.
   */
  desconectar() {
    if (this.stompClient && this.isConnectedSubject.value) {
      this.stompClient.disconnect(() => {
        console.log('Desconectado del servidor');
        this.isConnectedSubject.next(false); // Actualiza el estado de conexión
      });
    } else {
      console.log('No estás conectado');
    }
  }

  /**
   * Envía un mensaje al servidor a través de WebSocket.
   * El mensaje siempre se envía a la ruta privada definida en el backend.
   * 
   * @param mensaje El mensaje que se enviará.
   */
  sendMessage(mensaje: any) {
    if (!this.isConnectedSubject.value) {
      console.error('No se puede enviar el mensaje. No estás conectado.');
      return;
    }

    console.log('Enviando mensaje privado:', mensaje);
    this.stompClient.send('/app/mensajePrivado', {}, JSON.stringify(mensaje)); // Envía el mensaje al backend
  }

  /**
   * Devuelve un observable que permite suscribirse a los mensajes entrantes.
   * 
   * @returns Un observable para escuchar mensajes.
   */
  getMessages() {
    return this.messageSubject.asObservable();
  }

  /**
   * Obtiene el historial de conversación entre dos usuarios a través de una solicitud HTTP.
   * 
   * @param emisor Nombre del usuario que envía los mensajes.
   * @param destinatario Nombre del usuario que recibe los mensajes.
   * @returns Un observable que emite el historial de mensajes.
   */
  getConversacion(emisor: string, destinatario: string) {
    return this.http.get<any[]>(`${this.API_URL}/conversacion/${emisor}/${destinatario}`);
  }
}
