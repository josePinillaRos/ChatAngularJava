/**
 * AuthService
 * 
 * Servicio que proporciona métodos para la autenticación de usuarios en la aplicación.
 * Incluye funcionalidades como registro, inicio de sesión, almacenamiento del token JWT
 * y verificación del estado de autenticación.
 * 
 * Author: Jose Pinilla
 */
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root', // Disponible como servicio único en toda la aplicación
})
export class AuthService {
  private baseUrl = 'http://localhost:8080/api/auth'; // URL base para las solicitudes al backend

  constructor(private http: HttpClient) {}

  /**
   * Registra un nuevo usuario en el sistema.
   * 
   * @param username El nombre de usuario para el registro.
   * @param password La contraseña del usuario.
   * @returns Un observable que emite la respuesta del servidor.
   */
  register(username: string, password: string): Observable<any> {
    const body = { username, password }; // Datos del usuario a enviar al backend
    return this.http.post(`${this.baseUrl}/register`, body);
  }

  /**
   * Inicia sesión y obtiene el token JWT del servidor.
   * 
   * @param username El nombre de usuario.
   * @param password La contraseña del usuario.
   * @returns Un observable que emite la respuesta con el token JWT.
   */
  login(username: string, password: string): Observable<{ token: string }> {
    const body = { username, password }; // Datos para el inicio de sesión
    return this.http.post<{ token: string }>(`${this.baseUrl}/login`, body).pipe(
      tap(response => {
        // Almacena el token en localStorage
        this.setToken(response.token);
        // Guarda también el nombre de usuario (opcional)
        localStorage.setItem('username', username);
      })
    );
  }

  /**
   * Almacena el token JWT en el almacenamiento local del navegador.
   * 
   * @param token El token JWT a guardar.
   */
  setToken(token: string): void {
    localStorage.setItem('token', token);
  }

  /**
   * Obtiene el token JWT almacenado en localStorage.
   * 
   * @returns El token JWT o `null` si no existe.
   */
  getToken(): string | null {
    return localStorage.getItem('token');
  }

  /**
   * Verifica si el usuario está autenticado.
   * 
   * @returns `true` si hay un token válido en localStorage, `false` en caso contrario.
   */
  isLoggedIn(): boolean {
    return !!this.getToken(); // Devuelve true si existe un token, false si no
  }

  /**
   * Cierra la sesión del usuario.
   * Elimina el token y el nombre de usuario del almacenamiento local.
   */
  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
  }
}
