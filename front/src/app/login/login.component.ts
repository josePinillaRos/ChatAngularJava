// src/app/login/login.component.ts

import { Component } from '@angular/core';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';
import { RouterModule } from '@angular/router';
import { AuthService } from '../auth/services/auth.service';
import { FormsModule } from '@angular/forms'; // Importa FormsModule



@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  standalone: true, // Asegúrate de que esto está configurado si es standalone
  imports: [FormsModule,  RouterModule]
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(private router: Router, private authService: AuthService) {}

  login() {
    // Validación básica
    if (!this.username || !this.password) {
      this.errorMessage = 'Por favor, completa todos los campos.';
      Swal.fire({
        title: 'Error',
        text: this.errorMessage,
        icon: 'error',
        confirmButtonText: 'Aceptar'
      });
      return;
    }

    // Llamar al servicio de login
    this.authService.login(this.username, this.password).subscribe({
      next: () => {
        Swal.fire({
          title: 'Login Exitoso',
          text: 'Has iniciado sesión correctamente.',
          icon: 'success',
          confirmButtonText: 'Aceptar'
        }).then(() => {
          this.router.navigate(['/chat']);
        });

        // Limpiar el formulario
        this.username = '';
        this.password = '';
        this.errorMessage = '';
      },
      error: (err) => {
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
