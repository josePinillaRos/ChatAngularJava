package com.jose.primerProyectoSockets;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@SpringBootApplication(scanBasePackages =
		{"com.jose.primerProyectoSockets.config",
		"com.jose.primerProyectoSockets.controller",
		"com.jose.primerProyectoSockets.models",
		"com.jose.primerProyectoSockets.services",
		"com.jose.primerProyectoSockets.security"
})
@EnableMongoRepositories(basePackages = "com.jose.primerProyectoSockets.repositories")
public class PrimerProyectoSocketsApplication {


	public static void main(String[] args) {
		SpringApplication.run(PrimerProyectoSocketsApplication.class, args);
	}
}

