package com.jose.primerProyectoSockets.controller;

import com.jose.primerProyectoSockets.models.Usuarios;
import com.jose.primerProyectoSockets.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // GET /api/users  -> retorna la lista de todos los usuarios
    @GetMapping
    public List<Usuarios> listarUsuarios() {
        return usuarioService.findAllUsers();
    }
}
