package com.jose.primerProyectoSockets.config;

import com.jose.primerProyectoSockets.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Desactivar CSRF usando una lambda
                .csrf(csrf -> csrf.disable())

                .cors(withDefaults())
                // Configurar autorizaciones usando lambdas
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/chat-websocket/**").permitAll()
                        .requestMatchers("/app/**").permitAll() // Para STOMP si lo deseas
                        .requestMatchers("/api/users/**").authenticated()
                        .requestMatchers("/api/mensajes/**").authenticated() // Rutas protegidas
                        .anyRequest().authenticated()
                )

                // Añadir el filtro JWT antes del filtro de autenticación de usuario
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
