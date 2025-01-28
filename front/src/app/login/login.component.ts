/**
 * LoginComponent
 * 
 * Componente que gestiona la lógica del formulario de inicio de sesión.
 * Permite al usuario ingresar sus credenciales, validarlas y redirigirse al chat en caso de éxito.
 * También muestra mensajes de error o confirmación utilizando SweetAlert2.
 * 
 * Author: Jose Pinilla
 */

import { Component } from '@angular/core';
import Swal from 'sweetalert2'; // Importación de SweetAlert2 para mensajes visuales
import { Router } from '@angular/router'; // Para la navegación entre rutas
import { RouterModule } from '@angular/router';
import { AuthService } from '../auth/services/auth.service'; // Servicio para autenticación
import { FormsModule } from '@angular/forms'; // Importa FormsModule para manejar formularios

@Component({
  selector: 'app-login', // Selector para utilizar este componente
  templateUrl: './login.component.html', // Ruta al archivo de la plantilla HTML
  styleUrls: ['./login.component.css'], // Ruta al archivo de estilos CSS
  standalone: true, // Indica que este componente es independiente
  imports: [FormsModule, RouterModule] // Importa módulos necesarios para el componente
})
export class LoginComponent {
  username: string = ''; // Campo para el nombre de usuario
  password: string = ''; // Campo para la contraseña
  errorMessage: string = ''; // Mensaje de error para mostrar en el formulario

  constructor(private router: Router, private authService: AuthService) {}

  /**
   * Maneja la lógica para iniciar sesión.
   * Realiza validaciones básicas, llama al servicio de autenticación y muestra mensajes visuales.
   */
  login() {
    // Validación básica: Comprobar que los campos no estén vacíos
    if (!this.username || !this.password) {
      this.errorMessage = 'Por favor, completa todos los campos.';
      Swal.fire({
        title: 'Error', // Título del mensaje
        text: this.errorMessage, // Texto del mensaje
        icon: 'error', // Tipo de ícono (error)
        confirmButtonText: 'Aceptar' // Texto del botón
      });
      return; // Detiene la ejecución si no se completaron los campos
    }

    // Llamar al servicio de login
    this.authService.login(this.username, this.password).subscribe({
      next: () => {
        // Si el inicio de sesión es exitoso, muestra un mensaje de confirmación
        Swal.fire({
          title: 'Login Exitoso',
          text: 'Has iniciado sesión correctamente.',
          icon: 'success',
          confirmButtonText: 'Aceptar'
        }).then(() => {
          // Redirige al usuario al chat
          this.router.navigate(['/chat']);
        });

        // Limpiar los campos del formulario
        this.username = '';
        this.password = '';
        this.errorMessage = '';
      },
      error: (err) => {
        // Si ocurre un error, muestra un mensaje de error
        this.errorMessage = err.error.message || 'Usuario o contraseña incorrectos.';
        Swal.fire({
          title: 'INCORRECTO',
          text: this.errorMessage,
          icon: 'error',
          confirmButtonText: 'Aceptar'
        });
      }
    });
  }
}
