package com.jose.primerProyectoSockets.services;

import com.jose.primerProyectoSockets.models.Usuarios;
import com.jose.primerProyectoSockets.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

/**
 * class UsuarioService
 *
 * Servicio que proporciona la lógica de negocio relacionada con los usuarios.
 * Incluye métodos para registrar usuarios con contraseñas encriptadas, verificar la existencia de usuarios,
 * validar credenciales para el inicio de sesión y obtener todos los usuarios de la base de datos.
 *
 * Author: Jose Pinilla
 */
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Bean inyectado para gestionar la encriptación de contraseñas

    /**
     * Registra un nuevo usuario en la base de datos con la contraseña encriptada.
     *
     * @param username el nombre de usuario único.
     * @param password la contraseña en texto plano que será encriptada antes de guardarse.
     * @return el usuario registrado con la contraseña encriptada.
     */
    public Usuarios registrarUsuario(String username, String password) {
        String encodedPassword = passwordEncoder.encode(password); // Encripta la contraseña
        Usuarios usuario = new Usuarios(username, encodedPassword); // Crea una nueva instancia de usuario
        return usuarioRepository.save(usuario); // Guarda el usuario en la base de datos
    }

    /**
     * Verifica si un nombre de usuario ya existe en la base de datos.
     *
     * @param username el nombre de usuario a verificar.
     * @return true si el usuario existe, false en caso contrario.
     */
    public boolean existeUsuario(String username) {
        return usuarioRepository.findByUsername(username).isPresent();
    }

    /**
     * Valida las credenciales para el inicio de sesión.
     * Comprueba si el usuario existe y si la contraseña proporcionada coincide con la almacenada.
     *
     * @param username el nombre de usuario.
     * @param password la contraseña en texto plano proporcionada para validación.
     * @return true si las credenciales son válidas, false en caso contrario.
     */
    public boolean validarCredenciales(String username, String password) {
        Optional<Usuarios> usuarioOpt = usuarioRepository.findByUsername(username);

        // Retorna false si el usuario no existe
        if (usuarioOpt.isEmpty()) {
            return false;
        }

        // Valida la contraseña proporcionada contra la contraseña encriptada almacenada
        return passwordEncoder.matches(password, usuarioOpt.get().getPassword());
    }

    /**
     * Obtiene todos los usuarios registrados en la base de datos.
     *
     * @return una lista de usuarios.
     */
    public List<Usuarios> findAllUsers() {
        return usuarioRepository.findAll();
    }
}

