import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable({ providedIn: 'root' })
export class UserService {
  private baseUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) {}

  getAllUsers(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(this.baseUrl); 
    // Tienes que crear o inferir la interfaz "Usuario"
  }
}
export interface Usuario {
    id?: string;
    username: string;
    password?: string;  // o lo quitas si no quieres manejarlo en front
  }