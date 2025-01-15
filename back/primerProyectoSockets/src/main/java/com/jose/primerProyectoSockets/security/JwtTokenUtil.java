// src/main/java/com/jose/primerProyectoSockets/security/JwtTokenUtil.java

package com.jose.primerProyectoSockets.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {

    private final String secret = "TuSecretoClaveParaJWT"; // Cambia esto a un valor seguro

    // Extraer el username del token
    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    // Extraer la fecha de expiración del token
    public Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token).getExpiration();
    }

    // Obtener claims del token
    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    // Validar el token
    public boolean validateToken(String token, org.springframework.security.core.userdetails.UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Verificar si el token está expirado
    private boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // Generar un token
    public String generateToken(org.springframework.security.core.userdetails.UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
