// src/main/java/com/jose/primerProyectoSockets/repository/UserRepository.java

package com.jose.primerProyectoSockets.repositories;


import com.jose.primerProyectoSockets.models.Usuarios;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuarios, String> {
    Optional<Usuarios> findByUsername(String username);
}

