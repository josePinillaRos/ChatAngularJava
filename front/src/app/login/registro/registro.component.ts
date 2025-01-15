import { Component } from '@angular/core';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms'; // Importa FormsModule
import { AuthService } from '../../auth/services/auth.service';
import { RouterModule } from '@angular/router';
@Component({
  selector: 'app-registro',
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.css'],
  standalone: true, // Asegúrate de que esto está configurado si es standalone
  imports: [FormsModule, RouterModule, FormsModule] // Añade FormsModule aquí
})
export class RegistroComponent {
  nombre: string = '';
  username: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(private router: Router, private authService: AuthService) {}

  registrar() {
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

    // Llamar al servicio de registro
    this.authService.register(this.username, this.password).subscribe({
      next: () => {
        Swal.fire({
          title: 'Registro Exitoso',
          text: 'Tu cuenta ha sido creada correctamente.',
          icon: 'success',
          confirmButtonText: 'Aceptar'
        }).then(() => {
          this.router.navigate(['/login']);
        });

        // Limpiar el formulario
        this.username = '';
        this.password = '';
        this.errorMessage = '';
      },
      error: (err) => {
        this.errorMessage = err.error.message || 'Exito al registrar el usuario.';
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


