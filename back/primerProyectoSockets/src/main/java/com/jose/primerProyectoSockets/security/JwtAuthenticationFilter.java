// src/main/java/com/jose/primerProyectoSockets/security/JwtAuthenticationFilter.java

package com.jose.primerProyectoSockets.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * class JwtAuthenticationFilter
 *
 * Filtro personalizado que extiende `OncePerRequestFilter` para interceptar cada solicitud HTTP
 * y validar el token JWT presente en el encabezado `Authorization`. Si el token es válido, se
 * establece la autenticación en el contexto de seguridad de Spring Security.
 *
 * Author: Jose Pinilla
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil; // Utilidad para gestionar y validar tokens JWT

    @Autowired
    private CustomUserDetailsService customUserDetailsService; // Servicio para cargar detalles del usuario

    /**
     * Método principal del filtro que intercepta cada solicitud HTTP y valida el token JWT.
     *
     * @param request la solicitud HTTP entrante.
     * @param response la respuesta HTTP.
     * @param filterChain la cadena de filtros que continúa el procesamiento.
     * @throws ServletException en caso de error relacionado con la solicitud.
     * @throws IOException en caso de error de entrada/salida.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Extraer el encabezado Authorization
        String header = request.getHeader("Authorization");
        String token = null;
        String username = null;

        // Verificar que el encabezado contiene un token válido
        if (header != null && header.startsWith("Bearer ")) {
            // Extraer el token del encabezado (omitiendo el prefijo "Bearer ")
            token = header.substring(7);
            try {
                // Obtener el nombre de usuario contenido en el token
                username = jwtUtil.getUsernameFromToken(token);
            } catch (Exception e) {
                logger.error("Error al obtener el username del token JWT", e);
            }
        }

        // Validar el token y establecer la autenticación si no está ya configurada
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Cargar los detalles del usuario desde el servicio
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            // Validar el token JWT
            if (jwtUtil.validateToken(token)) {
                // Crear un objeto de autenticación para el usuario
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                // Configurar detalles adicionales de la solicitud
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Establecer la autenticación en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}


