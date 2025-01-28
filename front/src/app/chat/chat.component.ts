/**
 * ChatComponent
 * 
 * Componente que gestiona la interfaz del chat. Permite enviar y recibir mensajes, 
 * conectarse al servicio de chat y manejar las conversaciones entre usuarios.
 * 
 * Author: Jose Pinilla
 */

import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { ChatService } from '../chat.service'; // Servicio para manejar la lógica del chat
import { AuthService } from '../auth/services/auth.service'; // Servicio de autenticación
import { UserService } from '../auth/services/user.service'; // Servicio para obtener la lista de usuarios
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule], // Módulos utilizados en el componente
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css'],
})
export class ChatComponent implements OnInit {
  mensajes: any[] = []; // Array para almacenar mensajes de la conversación actual
  mensaje: string = ''; // Texto del mensaje que se va a enviar
  username: string = ''; // Nombre de usuario actual
  listaUsuarios: any[] = []; // Lista de usuarios disponibles para chatear
  seleccionado: string = ''; // Usuario seleccionado para la conversación
  color: string = this.getRandomColor(); // Color aleatorio asignado al usuario actual
  conectado: boolean = false; // Estado de conexión al servicio de chat

  constructor(
    private router: Router,
    public chatService: ChatService, // Servicio de chat inyectado
    private authService: AuthService, // Servicio de autenticación inyectado
    private userService: UserService // Servicio para manejar usuarios inyectado
  ) {}

  /**
   * Se ejecuta al inicializar el componente.
   * - Recupera el nombre de usuario desde el localStorage.
   * - Obtiene la lista de usuarios excluyendo al usuario actual.
   */
  ngOnInit(): void {
    const storedUsername = localStorage.getItem('username');
    this.username = storedUsername ? storedUsername : 'UsuarioSinLogin';

    // Cargar lista de usuarios excluyendo al usuario actual
    this.userService.getAllUsers().subscribe({
      next: (usuarios) => {
        this.listaUsuarios = usuarios.filter((u: any) => u.username !== this.username);
      },
      error: (err) => console.error('Error al obtener usuarios', err),
    });
  }

  /**
   * Conecta al servicio de chat y suscribe a los mensajes entrantes.
   */
  conectar() {
    this.chatService.conectar();
    this.conectado = true;
    this.chatService.getMessages().subscribe((mensaje) => {
      console.log('Mensaje privado recibido:', mensaje);
      if (this.esDeLaConversacion(mensaje)) {
        this.mensajes.push(mensaje);
      } else {
        console.log('Mensaje es de otra conversación, no se muestra en este chat');
      }
    });
  }

  /**
   * Desconecta del servicio de chat.
   */
  desconectar() {
    this.chatService.desconectar();
    this.conectado = false;
  }

  /**
   * Carga el historial de mensajes con un usuario específico.
   * 
   * @param destino Usuario con el que se desea cargar la conversación.
   */
  cargarConversacionCon(destino: string) {
    this.mensajes = []; // Limpia los mensajes actuales
    this.chatService.getConversacion(this.username, destino).subscribe({
      next: (msgs) => {
        this.mensajes = msgs;
        console.log('Conversacion con', destino, msgs);
      },
      error: (err) => console.error('Error al cargar conversación', err),
    });
  }

  /**
   * Envía un mensaje al usuario seleccionado y lo agrega al chat actual.
   */
  enviarMensaje() {
    const nuevoMensaje = {
      autor: this.username,
      username: this.username,
      destinatario: this.seleccionado,
      color: this.color,
      contenido: this.mensaje,
    };
    this.chatService.sendMessage(nuevoMensaje); // Envía el mensaje al backend
    this.mensajes.push(nuevoMensaje); // Lo agrega al chat actual
    this.mensaje = ''; // Limpia el campo de texto
  }

  /**
   * Verifica si un mensaje pertenece a la conversación actual.
   * 
   * @param mensaje Mensaje recibido.
   * @returns `true` si el mensaje pertenece a la conversación actual, de lo contrario `false`.
   */
  private esDeLaConversacion(mensaje: any): boolean {
    return (
      (mensaje.username === this.username && mensaje.destinatario === this.seleccionado) ||
      (mensaje.username === this.seleccionado && mensaje.destinatario === this.username)
    );
  }

  /**
   * Genera un color aleatorio en formato hexadecimal.
   * 
   * @returns Una cadena con el color generado.
   */
  private getRandomColor(): string {
    const letters = '0123456789ABCDEF';
    let color = '#';
    for (let i = 0; i < 6; i++) {
      color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
  }
}
