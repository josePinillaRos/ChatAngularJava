package com.jose.primerProyectoSockets.services;

import com.jose.primerProyectoSockets.models.Mensaje;
import com.jose.primerProyectoSockets.repositories.MensajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


/**
 * class MensajeService
 *
 * Servicio que proporciona la lógica de negocio para gestionar mensajes en la aplicación.
 * Incluye métodos para guardar mensajes, obtener todos los mensajes, filtrar mensajes
 * por usuario, y obtener conversaciones entre dos usuarios específicos.
 *
 * Author: Jose Pinilla
 */
@Service
public class MensajeService {

    @Autowired
    private MensajeRepository mensajeRepository;

    /**
     * Guarda un mensaje en la base de datos.
     *
     * @param mensaje el objeto Mensaje a guardar.
     * @return el mensaje guardado.
     */
    public Mensaje guardarMensaje(Mensaje mensaje) {
        return mensajeRepository.save(mensaje);
    }

    /**
     * Obtiene todos los mensajes almacenados en la base de datos.
     * Este método puede ser útil para depuración o funcionalidades administrativas.
     *
     * @return una lista de todos los mensajes.
     */
    public List<Mensaje> obtenerTodosLosMensajes() {
        return mensajeRepository.findAll();
    }

    /**
     * Obtiene todos los mensajes asociados a un usuario específico.
     *
     * @param username el nombre del usuario cuyo historial de mensajes se desea obtener.
     * @return una lista de mensajes enviados por el usuario.
     */
    public List<Mensaje> obtenerMensajesPorUsuario(String username) {
        return mensajeRepository.findByUsername(username);
    }

    /**
     * Obtiene una conversación entre dos usuarios específicos.
     *
     * @param emisor el nombre del usuario que envió los mensajes.
     * @param receptor el nombre del usuario que recibió los mensajes.
     * @return una lista de mensajes intercambiados entre el emisor y el receptor.
     */
    public List<Mensaje> obtenerConversacion(String emisor, String receptor) {
        return mensajeRepository.findConversacion(emisor, receptor);
    }
}



