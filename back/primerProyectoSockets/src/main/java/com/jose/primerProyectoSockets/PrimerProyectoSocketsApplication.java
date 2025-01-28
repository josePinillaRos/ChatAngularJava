package com.jose.primerProyectoSockets;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


/**
 * class PrimerProyectoSocketsApplication
 *
 * Clase principal de la aplicación Spring Boot que inicia la ejecución del proyecto.
 * Configura los paquetes a escanear para encontrar los componentes, servicios, controladores y configuraciones.
 * También habilita los repositorios de MongoDB para gestionar la persistencia de datos.
 *
 * Author: Jose Pinilla
 */
@SpringBootApplication(scanBasePackages = {
		"com.jose.primerProyectoSockets.config",       // Configuraciones de la aplicación
		"com.jose.primerProyectoSockets.controller",   // Controladores REST
		"com.jose.primerProyectoSockets.models",       // Modelos de datos
		"com.jose.primerProyectoSockets.services",     // Servicios de lógica de negocio
		"com.jose.primerProyectoSockets.security"      // Configuraciones y utilidades de seguridad
})
@EnableMongoRepositories(basePackages = "com.jose.primerProyectoSockets.repositories") // Habilita repositorios de MongoDB
public class PrimerProyectoSocketsApplication {

	/**
	 * Método principal que arranca la aplicación Spring Boot.
	 *
	 * @param args argumentos de línea de comandos (opcional).
	 */
	public static void main(String[] args) {
		SpringApplication.run(PrimerProyectoSocketsApplication.class, args);
	}
}


