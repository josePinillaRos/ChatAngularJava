package com.jose.primerProyectoSockets.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * class CorsConfig
 *
 * Clase de configuración para habilitar y gestionar las reglas de CORS (Cross-Origin Resource Sharing)
 * en la aplicación. Permite definir los orígenes, métodos y encabezados permitidos para las solicitudes HTTP.
 *
 * Author: Jose Pinilla
 */
@Configuration
public class CorsConfig {

    /**
     * Método que define un bean para la configuración de CORS en la aplicación.
     *
     * @return una instancia de WebMvcConfigurer que configura las reglas de CORS.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }
}

