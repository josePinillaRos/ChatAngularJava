// src/app/auth/interceptors/jwt.interceptor.ts

/**
 * JwtInterceptor
 * 
 * Interceptor HTTP de Angular que agrega el token JWT al encabezado `Authorization` 
 * de las solicitudes salientes (excepto las solicitudes de autenticación). Esto asegura 
 * que las solicitudes a rutas protegidas incluyan el token para la validación en el backend.
 * 
 * Author: Jose Pinilla
 */
import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {

  /**
   * Constructor que inyecta el servicio de autenticación.
   * 
   * @param authService Servicio que gestiona la autenticación del usuario y el token JWT.
   */
  constructor(private authService: AuthService) {}

  /**
   * Intercepta solicitudes HTTP para agregar el token JWT al encabezado `Authorization`.
   * 
   * @param request La solicitud HTTP que está siendo enviada.
   * @param next El siguiente manejador de la cadena de interceptores.
   * @returns Un observable que representa el flujo de eventos de la solicitud.
   */
  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const token = this.authService.getToken(); // Obtiene el token JWT del servicio AuthService

    // Agregar el token solo si no es una solicitud al endpoint de autenticación
    if (token && !request.url.includes('/api/auth/')) {
      // Clona la solicitud original y agrega el encabezado Authorization con el token JWT
      const cloned = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}` // Encabezado estándar para JWT
        }
      });
      // Pasa la solicitud clonada al siguiente manejador
      return next.handle(cloned);
    }

    // Si no hay token o es una solicitud al endpoint de autenticación, pasa la solicitud original
    return next.handle(request);
  }
}


