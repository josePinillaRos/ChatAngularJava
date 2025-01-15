package com.jose.primerProyectoSockets.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usuarios")
public class Usuarios {

    @Id
    private String id;

    private String username; // Renombrado de userName a username
    private String password; // Hasheada idealmente

    public Usuarios() {
    }

    public Usuarios(String username, String password) { // Actualizado el parámetro
        this.username = username;
        this.password = password;
    }

    // Getters y setters
    public String getId() {
        return id;
    }

    public String getUsername() { // Actualizado el getter
        return username;
    }

    public void setUsername(String username) { // Actualizado el setter
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}