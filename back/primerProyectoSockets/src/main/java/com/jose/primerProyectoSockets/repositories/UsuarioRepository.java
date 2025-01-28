// src/main/java/com/jose/primerProyectoSockets/repository/UserRepository.java

package com.jose.primerProyectoSockets.repositories;


import com.jose.primerProyectoSockets.models.Usuarios;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * interface UsuarioRepository
 *
 * Repositorio que proporciona métodos de acceso y manipulación de la colección "usuarios" en MongoDB.
 * Extiende `MongoRepository` para heredar operaciones CRUD básicas y define un método personalizado
 * para buscar usuarios por su nombre de usuario.
 *
 * Author: Jose Pinilla
 */
@Repository
public interface UsuarioRepository extends MongoRepository<Usuarios, String> {

    /**
     * Busca un usuario por su nombre de usuario único.
     *
     * @param username el nombre de usuario que se desea buscar.
     * @return un `Optional` que contiene el usuario si existe, o vacío si no se encuentra.
     */
    Optional<Usuarios> findByUsername(String username);
}


