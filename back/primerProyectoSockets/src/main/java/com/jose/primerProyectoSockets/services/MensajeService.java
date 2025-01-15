package com.jose.primerProyectoSockets.services;

import com.jose.primerProyectoSockets.models.Mensaje;
import com.jose.primerProyectoSockets.repositories.MensajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class MensajeService {

    @Autowired
    private MensajeRepository mensajeRepository;

    // Guardar un mensaje
    public Mensaje guardarMensaje(Mensaje mensaje) {
        return mensajeRepository.save(mensaje);
    }

    // Obtener todos los mensajes (por si acaso)
    public List<Mensaje> obtenerTodosLosMensajes() {
        return mensajeRepository.findAll();
    }

    // Obtener mensajes de un usuario específico
    public List<Mensaje> obtenerMensajesPorUsuario(String username) {
        return mensajeRepository.findByUsername(username);
    }

    // Nuevo: Obtener la conversación entre dos usuarios
    public List<Mensaje> obtenerConversacion(String emisor, String receptor) {
        return mensajeRepository.findConversacion(emisor, receptor);
    }
}


