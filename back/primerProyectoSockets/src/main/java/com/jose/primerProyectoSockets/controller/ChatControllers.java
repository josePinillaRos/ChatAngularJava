package com.jose.primerProyectoSockets.controller;

import com.jose.primerProyectoSockets.models.Mensaje;
import com.jose.primerProyectoSockets.repositories.MensajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class ChatControllers {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MensajeRepository mensajeRepository;

    @MessageMapping("/mensajePrivado")
    public void manejarMensajePrivado(Mensaje mensaje) {
        System.out.println("Mensaje enviado a: /topic/privado/" + mensaje.getDestinatario());

        mensaje.setFechaEnvio(LocalDateTime.now());
        Mensaje mensajeGuardado = mensajeRepository.save(mensaje);

        String destino = "/topic/privado/" + mensaje.getDestinatario();
        messagingTemplate.convertAndSend(destino, mensajeGuardado);

        System.out.println("Mensaje enviado a: " + destino);
    }
}
