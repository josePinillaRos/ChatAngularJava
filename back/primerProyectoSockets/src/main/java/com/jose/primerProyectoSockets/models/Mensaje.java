package com.jose.primerProyectoSockets.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDateTime;


/**
 * class Mensaje
 *
 * Representa la entidad "Mensaje" utilizada en la aplicación. Este modelo está vinculado a
 * una colección en MongoDB y se utiliza para almacenar los datos de los mensajes enviados
 * entre usuarios, incluyendo detalles como el autor, destinatario, contenido, color del texto y
 * fecha de envío.
 *
 * Author: Jose Pinilla
 */
public class Mensaje {

    @Id
    private String id; // Identificador único del mensaje

    private String autor; // Nombre del autor del mensaje
    private String username; // Nombre de usuario único asociado al autor
    private String destinatario; // Nombre del destinatario del mensaje
    private String contenido; // Contenido o texto del mensaje
    private String color; // Color del texto del mensaje
    private LocalDateTime fechaEnvio; // Fecha y hora en la que se envió el mensaje

    /**
     * Constructor vacío necesario para el funcionamiento de algunas librerías y frameworks
     * como MongoDB.
     */
    public Mensaje() {}

    /**
     * Constructor completo para inicializar todos los campos del mensaje.
     *
     * @param autor nombre del autor del mensaje.
     * @param username identificador único del autor.
     * @param contenido texto del mensaje.
     * @param color color del texto del mensaje.
     * @param fechaEnvio fecha y hora del envío del mensaje.
     */
    public Mensaje(String autor, String username, String contenido, String color, LocalDateTime fechaEnvio) {
        this.autor = autor;
        this.username = username;
        this.contenido = contenido;
        this.color = color;
        this.fechaEnvio = fechaEnvio;
    }

    // Getters y Setters para cada campo de la entidad

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }
}


