// src/main/java/com/jose/primerProyectoSockets/security/CustomUserDetailsService.java

package com.jose.primerProyectoSockets.security;

import com.jose.primerProyectoSockets.models.Usuarios;
import com.jose.primerProyectoSockets.repositories.UsuarioRepository;// Asegúrate de tener un UserRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * class CustomUserDetailsService
 *
 * Servicio personalizado para cargar los detalles de un usuario desde la base de datos.
 * Implementa la interfaz `UserDetailsService` de Spring Security, que se utiliza durante
 * la autenticación para validar las credenciales de un usuario.
 *
 * Author: Jose Pinilla
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository userRepository; // Repositorio para acceder a los usuarios en la base de datos

    /**
     * Carga un usuario por su nombre de usuario (username) desde la base de datos.
     *
     * @param username el nombre de usuario a buscar.
     * @return una instancia de `UserDetails` con la información del usuario.
     * @throws UsernameNotFoundException si el usuario no existe en la base de datos.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca al usuario en la base de datos
        Usuarios user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // Construye y retorna un objeto UserDetails para el usuario encontrado
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername()) // Nombre de usuario
                .password(user.getPassword()) // Contraseña (debe estar encriptada)
                .roles("USER") // Asigna el rol "USER" por defecto (ajústalo según tu lógica)
                .build();
    }
}
