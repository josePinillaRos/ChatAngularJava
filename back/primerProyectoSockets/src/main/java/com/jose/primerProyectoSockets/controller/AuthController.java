package com.jose.primerProyectoSockets.controller;

import com.jose.primerProyectoSockets.security.JwtUtil;
import com.jose.primerProyectoSockets.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    // Modelos de request del front
    public static class RegisterRequest {
        public String username;
        public String password;
    }

    public static class LoginRequest {
        public String username;
        public String password;
    }

    // Modelo de respuesta para el token
    public static class AuthResponse {
        public String token;
        public AuthResponse(String token) {
            this.token = token;
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        // 1) Comprobar si ya existe el usuario
        if (usuarioService.existeUsuario(request.username)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El usuario '" + request.username + "' ya existe en la base de datos.");
        }
        // 2) Registrar
        usuarioService.registrarUsuario(request.username, request.password);
        return ResponseEntity.ok("Usuario registrado exitosamente");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        boolean valid = usuarioService.validarCredenciales(request.username, request.password);

        if (!valid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales inválidas");
        }

        // Generar token JWT y retornarlo
        String token = jwtUtil.generateToken(request.username);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
