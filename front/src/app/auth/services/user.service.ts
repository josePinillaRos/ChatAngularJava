/**
 * UserService
 * 
 * Servicio para gestionar la comunicación con el backend relacionada con los usuarios.
 * Proporciona métodos para obtener la lista de usuarios desde la API del backend.
 * 
 * Author: Jose Pinilla
 */

import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root' // El servicio está disponible en toda la aplicación como un singleton
})
export class UserService {
  private baseUrl = 'http://localhost:8080/api/users'; // URL base para las solicitudes relacionadas con usuarios

  constructor(private http: HttpClient) {}

  /**
   * Obtiene la lista de todos los usuarios desde el backend.
   * 
   * @returns Un observable que emite un array de objetos de tipo Usuario.
   */
  getAllUsers(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(this.baseUrl); 
    // Realiza una solicitud GET al endpoint definido en baseUrl
  }
}

/**
 * Interfaz Usuario
 * 
 * Representa la estructura de los datos del usuario en el frontend.
 */
export interface Usuario {
  id?: string;        // Identificador único del usuario (opcional porque puede no ser necesario en algunas operaciones)
  username: string;   // Nombre de usuario
  password?: string;  // Contraseña (opcional; generalmente no se maneja en el frontend por razones de seguridad)
}
