import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { ChatService } from '../chat.service';
import { AuthService } from '../auth/services/auth.service';
import { UserService } from '../auth/services/user.service'; // <-- Aquí tu servicio para obtener usuarios
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [CommonModule, FormsModule,RouterModule],
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css'],
})
export class ChatComponent implements OnInit {
  mensajes: any[] = [];
  mensaje: string = '';

  username: string = '';
  listaUsuarios: any[] = [];
  seleccionado: string = ''; // El usuario con quien charlo

  color: string = this.getRandomColor();
  conectado: boolean = false;

  constructor(
    private router: Router,
    public chatService: ChatService,
    private authService: AuthService,
    private userService: UserService
  ) {}


  ngOnInit(): void {
    const storedUsername = localStorage.getItem('username');
    this.username = storedUsername ? storedUsername : 'UsuarioSinLogin';

    // Suscribirse a mensajes entrantes
    this.chatService.getMessages().subscribe((mensaje) => {
      console.log('Mensaje privado recibido:', mensaje);
      // Verificar si es parte de la conversación actual
      if (this.esDeLaConversacion(mensaje)) {
        this.mensajes.push(mensaje);
      } else {
        // Puedes ignorar o mostrar alerta, depende de tu UX
        console.log('Mensaje es de otra conversación, no se muestra en este chat');
      }
    });

    // Cargar lista de usuarios
    this.userService.getAllUsers().subscribe({
      next: (usuarios) => {
        this.listaUsuarios = usuarios.filter((u: any) => u.username !== this.username);
      },
      error: (err) => console.error('Error al obtener usuarios', err),
    });
  }

  conectar() {
    this.chatService.conectar();
    this.conectado = true;
    this.chatService.getMessages().subscribe((mensaje) => {
      console.log('Mensaje privado recibido:', mensaje);
      // Verificar si es parte de la conversación actual
      if (this.esDeLaConversacion(mensaje)) {
        this.mensajes.push(mensaje);
      } else {
        // Puedes ignorar o mostrar alerta, depende de tu UX
        console.log('Mensaje es de otra conversación, no se muestra en este chat');
      }
    });
  }

  desconectar() {
    this.chatService.desconectar();
    this.conectado = false;
  }

  // Al cambiar de 'seleccionado' en el <select>, obtener la conversación
  cargarConversacionCon(destino: string) {
    // Limpio el array de mensajes para mostrar solo esa conversación
    this.mensajes = [];
    // Llamo al endpoint para cargar histórico
    this.chatService.getConversacion(this.username, destino).subscribe({
      next: (msgs) => {
        // Guardo en local
        this.mensajes = msgs;
        console.log('Conversacion con', destino, msgs);
      },
      error: (err) => console.error('Error al cargar conversación', err),
    });
  }

  enviarMensaje() {
    const nuevoMensaje = {
      autor: this.username,
      username: this.username,
      destinatario: this.seleccionado,
      color: this.color,
      contenido: this.mensaje,
    };
    this.chatService.sendMessage(nuevoMensaje);
    this.mensaje = '';

    
  }

  private esDeLaConversacion(mensaje: any): boolean {
    // Un mensaje "pertenece" a la conversación actual si:
    // (emisor = this.username y destinatario = this.seleccionado) o
    // (emisor = this.seleccionado y destinatario = this.username)
    return (
      (mensaje.username === this.username && mensaje.destinatario === this.seleccionado) ||
      (mensaje.username === this.seleccionado && mensaje.destinatario === this.username)
    );
  }

  private getRandomColor(): string {
    const letters = '0123456789ABCDEF';
    let color = '#';
    for (let i = 0; i < 6; i++) {
      color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
  }
}