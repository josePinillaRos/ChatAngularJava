package com.jose.primerProyectoSockets.controller;

import com.jose.primerProyectoSockets.models.Mensaje;
import com.jose.primerProyectoSockets.services.MensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;


/**
 * class MensajeController
 *
 * Controlador REST que proporciona endpoints para gestionar mensajes en la aplicación.
 * Incluye funcionalidades para guardar mensajes, obtener todos los mensajes,
 * obtener mensajes de un usuario específico y obtener conversaciones entre dos usuarios.
 *
 * Author: Jose Pinilla
 */
@RestController
@RequestMapping("/api/mensajes")
public class MensajeController {

    @Autowired
    private MensajeService mensajeService;

    /**
     * Endpoint para guardar un mensaje en el sistema.
     *
     * @param mensaje objeto del mensaje recibido en el cuerpo de la solicitud.
     * @return el mensaje guardado después de persistirlo en la base de datos.
     */
    @PostMapping
    public Mensaje guardarMensaje(@RequestBody Mensaje mensaje) {
        return mensajeService.guardarMensaje(mensaje);
    }

    /**
     * Endpoint para obtener todos los mensajes almacenados en el sistema.
     *
     * @return una lista de todos los mensajes.
     */
    @GetMapping
    public List<Mensaje> obtenerTodosLosMensajes() {
        return mensajeService.obtenerTodosLosMensajes();
    }

    /**
     * Endpoint para obtener todos los mensajes de un usuario específico.
     *
     * @param username nombre del usuario del cual se desean obtener los mensajes.
     * @return una lista de mensajes asociados al usuario.
     */
    @GetMapping("/usuario/{username}")
    public List<Mensaje> obtenerMensajesPorUsuario(@PathVariable String username) {
        return mensajeService.obtenerMensajesPorUsuario(username);
    }

    /**
     * Endpoint para obtener la conversación entre dos usuarios.
     *
     * @param emisor nombre del usuario que envió los mensajes.
     * @param destinatario nombre del usuario que recibió los mensajes.
     * @return una lista de mensajes intercambiados entre el emisor y el destinatario.
     */
    @GetMapping("/conversacion/{emisor}/{destinatario}")
    public List<Mensaje> getConversacion(
            @PathVariable String emisor,
            @PathVariable String destinatario
    ) {
        return mensajeService.obtenerConversacion(emisor, destinatario);
    }
}

