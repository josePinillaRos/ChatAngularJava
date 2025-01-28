// src/main/java/com/jose/primerProyectoSockets/config/PasswordConfig.java

package com.jose.primerProyectoSockets.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * class PasswordConfig
 *
 * Clase de configuración para la codificación de contraseñas en la aplicación.
 * Define un bean de tipo `PasswordEncoder` para gestionar el hashing de contraseñas utilizando BCrypt.
 *
 * Author: Jose Pinilla
 */
@Configuration
public class PasswordConfig {

    /**
     * Método que expone un bean para el codificador de contraseñas.
     * Utiliza BCrypt, un algoritmo seguro para el hashing de contraseñas.
     *
     * @return una instancia de `PasswordEncoder` con implementación de `BCryptPasswordEncoder`.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}