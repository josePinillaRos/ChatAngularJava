// src/app/auth/guards/auth.guard.ts

/**
 * AuthGuard
 * 
 * Guard de Angular que protege rutas específicas verificando si el usuario está autenticado.
 * Si el usuario no está autenticado, lo redirige a la página de inicio de sesión.
 * 
 * Author: Jose Pinilla
 */
import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root' // Proporciona esta clase como un servicio único en toda la aplicación
})
export class AuthGuard implements CanActivate {

  /**
   * Constructor que inyecta las dependencias necesarias.
   * 
   * @param authService Servicio de autenticación utilizado para verificar si el usuario está autenticado.
   * @param router Router de Angular para redirigir al usuario en caso de que no esté autenticado.
   */
  constructor(private authService: AuthService, private router: Router) {}

  /**
   * Método `canActivate` que se ejecuta antes de cargar una ruta protegida.
   * 
   * @returns true si el usuario está autenticado, false si no lo está (y lo redirige a /login).
   */
  canActivate(): boolean {
    // Verifica si el usuario está autenticado llamando a `isLoggedIn` del servicio AuthService
    if (this.authService.isLoggedIn()) {
      return true; // Permite el acceso a la ruta protegida
    } else {
      this.router.navigate(['/login']); // Redirige al usuario a la página de inicio de sesión
      return false; // Bloquea el acceso a la ruta
    }
  }
}


