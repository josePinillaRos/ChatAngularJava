/**
 * RegistroComponent
 * 
 * Componente que gestiona el formulario de registro de usuarios. Permite a los usuarios
 * crear una nueva cuenta proporcionando un nombre de usuario y una contraseña.
 * Muestra mensajes de éxito o error utilizando SweetAlert2.
 * 
 * Author: Jose Pinilla
 */

import { Component } from '@angular/core';
import Swal from 'sweetalert2'; // Importación de SweetAlert2 para mensajes visuales
import { Router } from '@angular/router'; // Para redirigir entre rutas
import { FormsModule } from '@angular/forms'; // Módulo necesario para formularios
import { AuthService } from '../../auth/services/auth.service'; // Servicio de autenticación
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-registro', // Selector del componente
  templateUrl: './registro.component.html', // Ruta al archivo de la plantilla HTML
  styleUrls: ['./registro.component.css'], // Ruta al archivo de estilos CSS
  standalone: true, // Indica que este componente es independiente
  imports: [FormsModule, RouterModule] // Módulos importados necesarios para el componente
})
export class RegistroComponent {
  nombre: string = ''; // Campo para el nombre del usuario (no usado directamente aquí)
  username: string = ''; // Campo para el nombre de usuario
  password: string = ''; // Campo para la contraseña
  errorMessage: string = ''; // Mensaje de error para mostrar en el formulario

  constructor(private router: Router, private authService: AuthService) {}

  /**
   * Maneja el proceso de registro de usuarios.
   * Realiza validaciones básicas, llama al servicio de registro y muestra mensajes visuales.
   */
  registrar() {
    // Validación básica: Comprobar que los campos no estén vacíos
    if (!this.username || !this.password) {
      this.errorMessage = 'Por favor, completa todos los campos.';
      Swal.fire({
        title: 'Error', // Título del mensaje
        text: this.errorMessage, // Texto del mensaje
        icon: 'error', // Ícono (error)
        confirmButtonText: 'Aceptar' // Texto del botón
      });
      return; // Detener ejecución si faltan campos
    }

    // Llamar al servicio de registro
    this.authService.register(this.username, this.password).subscribe({
      next: () => {
        // Mostrar mensaje de éxito si el registro es exitoso
        Swal.fire({
          title: 'Registro Exitoso',
          text: 'Tu cuenta ha sido creada correctamente.',
          icon: 'success',
          confirmButtonText: 'Aceptar'
        }).then(() => {
          // Redirigir al usuario al login después del registro exitoso
          this.router.navigate(['/login']);
        });

        // Limpiar el formulario
        this.username = '';
        this.password = '';
        this.errorMessage = '';
      },
      error: (err) => {
        // Mostrar mensaje de error si el registro falla
        console.error('Error al registrar:', err);
        this.errorMessage = err.error.message || 'Error al registrar el usuario.';
        Swal.fire({
          title: 'Error',
          text: this.errorMessage,
          icon: 'error',
          confirmButtonText: 'Aceptar'
        });
      }
    });
  }
}
