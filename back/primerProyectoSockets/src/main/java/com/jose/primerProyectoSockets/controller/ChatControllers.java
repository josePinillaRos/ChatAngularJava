package com.jose.primerProyectoSockets.controller;

import com.jose.primerProyectoSockets.models.Mensaje;
import com.jose.primerProyectoSockets.repositories.MensajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

/**
 * class ChatControllers
 *
 * Controlador encargado de gestionar los mensajes privados enviados a través de WebSocket.
 * Utiliza la plantilla de mensajería de Spring para enviar mensajes a destinos específicos y persiste los mensajes en la base de datos.
 *
 * Author: Jose Pinilla
 */
@Controller
public class ChatControllers {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MensajeRepository mensajeRepository;

    /**
     * Método encargado de manejar mensajes privados enviados a través de WebSocket.
     * Recibe el mensaje, lo guarda en la base de datos y lo envía al destinatario correspondiente.
     *
     * @param mensaje objeto que representa el mensaje recibido, contiene el destinatario, contenido, etc.
     */
    @MessageMapping("/mensajePrivado")
    public void manejarMensajePrivado(Mensaje mensaje) {
        // Log para indicar el destinatario del mensaje
        System.out.println("Mensaje enviado a: /topic/privado/" + mensaje.getDestinatario());

        // Asigna la fecha de envío actual al mensaje
        mensaje.setFechaEnvio(LocalDateTime.now());

        // Guarda el mensaje en la base de datos
        Mensaje mensajeGuardado = mensajeRepository.save(mensaje);

        // Define el destino del mensaje (topic privado para el destinatario)
        String destino = "/topic/privado/" + mensaje.getDestinatario();

        // Envía el mensaje guardado al destino especificado mediante WebSocket
        messagingTemplate.convertAndSend(destino, mensajeGuardado);

        // Log para confirmar que el mensaje fue enviado
        System.out.println("Mensaje enviado a: " + destino);
    }
}

