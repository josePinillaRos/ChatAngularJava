package com.jose.primerProyectoSockets.controller;

import com.jose.primerProyectoSockets.models.Usuarios;
import com.jose.primerProyectoSockets.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * class UsuarioController
 *
 * Controlador REST que proporciona un endpoint para gestionar los usuarios de la aplicación.
 * Permite listar todos los usuarios registrados.
 *
 * Author: Jose Pinilla
 */
@RestController
@RequestMapping("/api/users")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Endpoint para listar todos los usuarios.
     *
     * @return una lista de todos los usuarios registrados en la aplicación.
     */
    @GetMapping
    public List<Usuarios> listarUsuarios() {
        return usuarioService.findAllUsers();
    }
}

