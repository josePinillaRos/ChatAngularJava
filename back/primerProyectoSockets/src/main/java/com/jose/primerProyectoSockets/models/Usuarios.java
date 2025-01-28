package com.jose.primerProyectoSockets.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * class Usuarios
 *
 * Representa la entidad "Usuarios" utilizada en la aplicación. Esta clase está vinculada
 * a la colección "usuarios" en MongoDB y almacena la información básica de cada usuario,
 * como su identificador único, nombre de usuario y contraseña.
 *
 * Author: Jose Pinilla
 */
@Document(collection = "usuarios")
public class Usuarios {

    @Id
    private String id; // Identificador único del usuario en la colección

    private String username; // Nombre de usuario único (renombrado de userName a username)
    private String password; // Contraseña del usuario (debe almacenarse hasheada para mayor seguridad)

    /**
     * Constructor vacío necesario para la serialización y deserialización.
     */
    public Usuarios() {
    }

    /**
     * Constructor que inicializa un objeto Usuario con nombre de usuario y contraseña.
     *
     * @param username el nombre de usuario único.
     * @param password la contraseña del usuario.
     */
    public Usuarios(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters y Setters

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
