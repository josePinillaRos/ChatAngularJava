package com.jose.primerProyectoSockets.repositories;

import com.jose.primerProyectoSockets.models.Mensaje;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensajeRepository extends MongoRepository<Mensaje, String> {

    List<Mensaje> findByUsername(String username);

    // Conversación bidireccional: emisor <-> destinatario
    @Query("{ $or: [ " +
            "{ $and: [ { username: ?0 }, { destinatario: ?1 } ] }, " +
            "{ $and: [ { username: ?1 }, { destinatario: ?0 } ] } " +
            "] }")
    List<Mensaje> findConversacion(String emisor, String destinatario);
}
