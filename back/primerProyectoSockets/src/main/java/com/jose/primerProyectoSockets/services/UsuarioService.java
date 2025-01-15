package com.jose.primerProyectoSockets.services;

import com.jose.primerProyectoSockets.models.Usuarios;
import com.jose.primerProyectoSockets.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Inyectamos el bean definido en PasswordConfig
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Registra (crea) un usuario con la contraseña encriptada.
     */
    public Usuarios registrarUsuario(String username, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        Usuarios usuario = new Usuarios(username, encodedPassword);
        return usuarioRepository.save(usuario);
    }

    /**
     * Verifica si el username ya está en la base de datos.
     */
    public boolean existeUsuario(String username) {
        return usuarioRepository.findByUsername(username).isPresent();
    }

    /**
     * Para el login: comprueba si existe el usuario y si la contraseña coincide.
     */
    public boolean validarCredenciales(String username, String password) {
        Optional<Usuarios> usuarioOpt = usuarioRepository.findByUsername(username);

        if (usuarioOpt.isEmpty()) {
            return false;  // no existe => no valida
        }

        // Si existe, comprobar el password hasheado
        return passwordEncoder.matches(password, usuarioOpt.get().getPassword());
    }

    /**
     * Devuelve todos los usuarios de la base de datos
     */
    public List<Usuarios> findAllUsers() {
        return usuarioRepository.findAll();
    }
}
