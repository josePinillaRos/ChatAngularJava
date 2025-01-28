package com.jose.primerProyectoSockets.config;

import com.jose.primerProyectoSockets.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * class SecurityConfig
 *
 * Clase de configuración para la seguridad de la aplicación. Configura el uso de JWT para la autenticación,
 * desactiva CSRF, habilita CORS, y define las reglas de autorización para las rutas.
 *
 * Author: Jose Pinilla
 */
@Configuration
public class SecurityConfig {

    // Filtro para gestionar la autenticación basada en JWT
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Método que configura la cadena de filtros de seguridad para la aplicación.
     * Define el comportamiento de seguridad para las rutas, habilita CORS, desactiva CSRF,
     * y establece el filtro JWT.
     *
     * @param http objeto de configuración de seguridad HTTP.
     * @return una instancia de `SecurityFilterChain` configurada.
     * @throws Exception en caso de error durante la configuración.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Desactiva la protección CSRF, útil cuando se utiliza autenticación JWT
                .csrf(csrf -> csrf.disable())

                // Habilita CORS para permitir solicitudes desde diferentes dominios
                .cors(withDefaults())

                // Configuración de autorización para las rutas de la aplicación
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/auth/**").permitAll() // Permite acceso público a las rutas de autenticación
                        .requestMatchers("/chat-websocket/**").permitAll() // Permite acceso público para WebSocket
                        .requestMatchers("/app/**").permitAll() // Rutas públicas para STOMP (opcional)
                        .requestMatchers("/api/users/**").authenticated() // Requiere autenticación para las rutas de usuarios
                        .requestMatchers("/api/mensajes/**").authenticated() // Requiere autenticación para las rutas de mensajes
                        .anyRequest().authenticated() // Cualquier otra ruta requiere autenticación
                )

                // Añade el filtro JWT antes del filtro estándar de autenticación
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

