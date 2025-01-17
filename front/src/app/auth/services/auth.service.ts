// src/app/auth/services/auth.service.ts

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private baseUrl = 'http://localhost:8080/api/auth'; // Ajusta según tu backend

  constructor(private http: HttpClient) {}

  register(username: string, password: string): Observable<any> {
    const body = { username, password }; 
    return this.http.post(`${this.baseUrl}/register`, body);
  }

  // Login y obtener el token
  login(username: string, password: string): Observable<{ token: string }> {
    const body = { username, password };
    return this.http.post<{ token: string }>(`${this.baseUrl}/login`, body).pipe(
      tap(response => {
        this.setToken(response.token);
        localStorage.setItem('username', username);
      })
    );
  }

  // Guardar el token en localStorage
  setToken(token: string): void {
    localStorage.setItem('token', token);
  }

  // Obtener el token desde localStorage
  getToken(): string | null {
    return localStorage.getItem('token');
  }

  // Verificar si el usuario está logueado
  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  // Logout del usuario
  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
  }
}

