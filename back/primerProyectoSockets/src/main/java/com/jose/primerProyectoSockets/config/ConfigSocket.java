package com.jose.primerProyectoSockets.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


/**
 * class ConfigSocket
 *
 * Clase de configuración para habilitar y gestionar la comunicación WebSocket en la aplicación.
 * Permite establecer puntos de conexión para clientes y configurar el broker de mensajes.
 *
 * Author: Jose Pinilla
 */
@Configuration
@EnableWebSocketMessageBroker
public class ConfigSocket implements WebSocketMessageBrokerConfigurer {

    /**
     * Método que registra los puntos de conexión (endpoints) para los clientes WebSocket.
     * En este caso, permite a los clientes conectarse a "/chat-websocket" usando SockJS.
     *
     * @param registry objeto que permite registrar los endpoints.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Nos indicará a dónde tenemos que conectarnos,
        // en este caso a http://localhost:4200/chat-websocket
        registry.addEndpoint("/chat-websocket")
                .setAllowedOrigins("http://localhost:4200") // Dominios permitidos
                .withSockJS();
    }

    /**
     * Método que configura el Message Broker, el cual es responsable de enrutar los mensajes.
     * Define los prefijos de los destinos de la aplicación y del broker.
     *
     * @param registry objeto que permite configurar el broker de mensajes.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Habilita un broker simple con prefijos para rutas de mensajes
        registry.enableSimpleBroker("/chat", "/queue", "/topic");
        // Define el prefijo para destinos dentro de la aplicación
        registry.setApplicationDestinationPrefixes("/app");
        // Define el prefijo para destinos específicos de usuarios
        registry.setUserDestinationPrefix("/user");
    }
}


