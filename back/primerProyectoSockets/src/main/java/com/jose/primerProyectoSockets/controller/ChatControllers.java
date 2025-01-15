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
    private MensajeRepository mensajeRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * Método para mensajes privados.
     * El front siempre hará stompClient.send("/app/mensajePrivado", ...).
     * y se suscribirá a "/user/queue/mensajes".
     */
    @MessageMapping("/mensajePrivado")
    public void mensajePrivado(Mensaje mensaje) {
        System.out.println("MENSAJE PRIVADO ENVIADO");
        mensaje.setFechaEnvio(LocalDateTime.now());

        if (mensaje.getUsername() == null || mensaje.getUsername().isEmpty()) {
            mensaje.setUsername("UsuarioAnónimo");
        }

        // Guardamos el mensaje en la base
        Mensaje mensajeGuardado = mensajeRepository.save(mensaje);

        // Enviar solo al destinatario a "/user/<destinatario>/queue/mensajes"
        messagingTemplate.convertAndSend(
                "/user/" + mensaje.getDestinatario() + "/queue/mensajes",
                mensajeGuardado
        );

        System.out.println("Mensaje privado guardado: " + mensajeGuardado);
    }
}
