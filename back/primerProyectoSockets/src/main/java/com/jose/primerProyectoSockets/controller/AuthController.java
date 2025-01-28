package com.jose.primerProyectoSockets.controller;

import com.jose.primerProyectoSockets.security.JwtUtil;
import com.jose.primerProyectoSockets.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

/**
 * class AuthController
 *
 * Controlador REST para gestionar la autenticación y el registro de usuarios.
 * Proporciona endpoints para el registro de nuevos usuarios y la generación de tokens JWT durante el inicio de sesión.
 *
 * Author: Jose Pinilla
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Modelo de datos para la solicitud de registro desde el cliente.
     * Contiene los campos `username` y `password` enviados desde el frontend.
     */
    public static class RegisterRequest {
        public String username;
        public String password;
    }

    /**
     * Modelo de datos para la solicitud de inicio de sesión desde el cliente.
     * Contiene los campos `username` y `password` enviados desde el frontend.
     */
    public static class LoginRequest {
        public String username;
        public String password;
    }

    /**
     * Modelo de respuesta para la autenticación.
     * Contiene un campo `token` que almacena el token JWT generado tras un inicio de sesión exitoso.
     */
    public static class AuthResponse {
        public String token;

        public AuthResponse(String token) {
            this.token = token;
        }
    }

    /**
     * Endpoint para registrar nuevos usuarios.
     *
     * @param request objeto con los datos de registro (username y password).
     * @return una respuesta HTTP indicando el resultado del registro.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        // Verifica si el usuario ya existe en la base de datos
        if (usuarioService.existeUsuario(request.username)) {
            // Retorna una respuesta con un mensaje de error en formato JSON
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("message",
                            "El usuario '" + request.username + "' ya existe en la base de datos."));
        }

        // Registra al usuario en el sistema
        usuarioService.registrarUsuario(request.username, request.password);

        // Retorna una respuesta con un mensaje de éxito en formato JSON
        return ResponseEntity.ok(Collections.singletonMap("message", "Usuario registrado exitosamente"));
    }

    /**
     * Endpoint para iniciar sesión y generar un token JWT.
     *
     * @param request objeto con los datos de inicio de sesión (username y password).
     * @return una respuesta HTTP con el token JWT en caso de éxito o un mensaje de error.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // Valida las credenciales del usuario
        boolean valid = usuarioService.validarCredenciales(request.username, request.password);

        if (!valid) {
            // Retorna una respuesta de error si las credenciales no son válidas
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales inválidas");
        }

        // Genera un token JWT para el usuario autenticado
        String token = jwtUtil.generateToken(request.username);

        // Retorna el token en un objeto de respuesta
        return ResponseEntity.ok(new AuthResponse(token));
    }
}

