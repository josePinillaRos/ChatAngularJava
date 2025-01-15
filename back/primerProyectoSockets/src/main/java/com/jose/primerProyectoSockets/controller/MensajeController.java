package com.jose.primerProyectoSockets.controller;

import com.jose.primerProyectoSockets.models.Mensaje;
import com.jose.primerProyectoSockets.services.MensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/api/mensajes")
public class MensajeController {


    @Autowired
    private MensajeService mensajeService;


    // Endpoint para guardar un mensaje
    @PostMapping
    public Mensaje guardarMensaje(@RequestBody Mensaje mensaje) {
        return mensajeService.guardarMensaje(mensaje);
    }


    // Endpoint para obtener todos los mensajes
    @GetMapping
    public List<Mensaje> obtenerTodosLosMensajes() {
        return mensajeService.obtenerTodosLosMensajes();
    }


    // Endpoint para obtener mensajes de un usuario específico
    @GetMapping("/usuario/{username}")
    public List<Mensaje> obtenerMensajesPorUsuario(@PathVariable String username) {
        return mensajeService.obtenerMensajesPorUsuario(username);
    }

    @GetMapping("/conversacion/{emisor}/{destinatario}")
    public List<Mensaje> getConversacion(
            @PathVariable String emisor,
            @PathVariable String destinatario
    ) {
        return mensajeService.obtenerConversacion(emisor, destinatario);
    }
}
