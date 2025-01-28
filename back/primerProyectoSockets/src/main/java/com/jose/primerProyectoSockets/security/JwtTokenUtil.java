// src/main/java/com/jose/primerProyectoSockets/security/JwtTokenUtil.java

package com.jose.primerProyectoSockets.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * class JwtTokenUtil
 *
 * Clase que proporciona utilidades para la gestión de tokens JWT (JSON Web Tokens).
 * Incluye métodos para generar, validar y extraer información de un token JWT.
 *
 * Author: Jose Pinilla
 */
@Component
public class JwtTokenUtil {

    // Clave secreta para firmar y validar tokens JWT (debe ser segura y almacenada de forma confidencial)
    private final String secret = "TuSecretoClaveParaJWT"; // Cambia esto a un valor seguro

    /**
     * Extrae el nombre de usuario (subject) del token JWT.
     *
     * @param token el token JWT.
     * @return el nombre de usuario contenido en el token.
     */
    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    /**
     * Extrae la fecha de expiración del token JWT.
     *
     * @param token el token JWT.
     * @return la fecha de expiración del token.
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token).getExpiration();
    }

    /**
     * Extrae todos los claims (información) del token JWT.
     *
     * @param token el token JWT.
     * @return los claims contenidos en el token.
     */
    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret) // Clave usada para firmar el token
                .parseClaimsJws(token) // Analiza y valida el token
                .getBody(); // Devuelve los claims del cuerpo del token
    }

    /**
     * Valida el token JWT verificando el nombre de usuario y si el token no ha expirado.
     *
     * @param token el token JWT.
     * @param userDetails detalles del usuario autenticado.
     * @return true si el token es válido, false en caso contrario.
     */
    public boolean validateToken(String token, org.springframework.security.core.userdetails.UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Verifica si el token JWT ha expirado.
     *
     * @param token el token JWT.
     * @return true si el token ha expirado, false en caso contrario.
     */
    private boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * Genera un nuevo token JWT para un usuario dado.
     *
     * @param userDetails detalles del usuario para quien se generará el token.
     * @return un token JWT firmado.
     */
    public String generateToken(org.springframework.security.core.userdetails.UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername()) // Establece el nombre de usuario como subject
                .setIssuedAt(new Date(System.currentTimeMillis())) // Fecha de emisión
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Expira en 10 horas
                .signWith(SignatureAlgorithm.HS512, secret) // Firma el token usando HS512 y la clave secreta
                .compact(); // Devuelve el token generado
    }
}

