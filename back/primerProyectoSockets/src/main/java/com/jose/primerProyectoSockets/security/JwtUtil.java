package com.jose.primerProyectoSockets.security;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * class JwtUtil
 *
 * Clase que proporciona utilidades para la gestión de tokens JWT (JSON Web Tokens).
 * Incluye métodos para generar, validar y extraer información de los tokens.
 *
 * Author: Jose Pinilla
 */
@Component
public class JwtUtil {

    // Clave secreta para firmar los tokens (debe ser segura y mantenerse confidencial)
    private final String SECRET_KEY = "EstaClaveDebeSerMuyMuySeguraYSecretaDeVerda123";

    /**
     * Genera un token JWT para un usuario dado.
     *
     * @param username el nombre de usuario para quien se generará el token.
     * @return el token JWT generado.
     */
    public String generateToken(String username) {
        Date now = new Date(); // Fecha y hora actuales
        Date expiryDate = new Date(now.getTime() + 3600_000); // Fecha de expiración (1 hora desde ahora)

        // Crear la clave de firma a partir de la clave secreta
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        // Construir y devolver el token JWT
        return Jwts.builder()
                .setSubject(username) // Establecer el subject como el nombre de usuario
                .setIssuedAt(now) // Fecha de emisión del token
                .setExpiration(expiryDate) // Fecha de expiración
                .signWith(key, SignatureAlgorithm.HS256) // Firmar el token con HS256 y la clave secreta
                .compact(); // Construir el token como una cadena compacta
    }

    /**
     * Valida un token JWT.
     *
     * @param token el token JWT que se desea validar.
     * @return true si el token es válido, false en caso contrario.
     */
    public boolean validateToken(String token) {
        try {
            // Verificar el token usando la clave secreta
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY.getBytes())
                    .build()
                    .parseClaimsJws(token);
            return true; // Si no lanza excepción, el token es válido
        } catch (JwtException e) {
            return false; // Si ocurre una excepción, el token no es válido
        }
    }

    /**
     * Extrae el nombre de usuario (subject) de un token JWT.
     *
     * @param token el token JWT del cual se desea extraer el nombre de usuario.
     * @return el nombre de usuario contenido en el token.
     */
    public String getUsernameFromToken(String token) {
        // Extraer los claims (datos) del token
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes()) // Clave de firma
                .build()
                .parseClaimsJws(token) // Analizar el token
                .getBody(); // Obtener el cuerpo de los claims
        return claims.getSubject(); // Devolver el subject (nombre de usuario)
    }
}
