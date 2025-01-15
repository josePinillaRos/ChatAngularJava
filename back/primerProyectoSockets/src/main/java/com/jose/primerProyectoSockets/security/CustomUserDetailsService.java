// src/main/java/com/jose/primerProyectoSockets/security/CustomUserDetailsService.java

package com.jose.primerProyectoSockets.security;
// Asegúrate de tener una clase User
import com.jose.primerProyectoSockets.models.Usuarios;
import com.jose.primerProyectoSockets.repositories.UsuarioRepository;// Asegúrate de tener un UserRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository userRepository; // Repositorio para acceder a los usuarios

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuarios user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword()) // Asegúrate de que la contraseña está encriptada
                .roles("USER") // Asigna roles según tu lógica
                .build();
    }
}