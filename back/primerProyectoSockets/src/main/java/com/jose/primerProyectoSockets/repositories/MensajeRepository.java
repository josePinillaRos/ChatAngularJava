package com.jose.primerProyectoSockets.repositories;

import com.jose.primerProyectoSockets.models.Mensaje;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * interface MensajeRepository
 *
 * Repositorio que proporciona métodos de acceso y manipulación de la colección "mensajes" en MongoDB.
 * Extiende `MongoRepository` para heredar operaciones CRUD básicas y define consultas personalizadas
 * para buscar mensajes por usuario y para obtener conversaciones bidireccionales entre usuarios.
 *
 * Author: Jose Pinilla
 */
@Repository
public interface MensajeRepository extends MongoRepository<Mensaje, String> {

    /**
     * Busca todos los mensajes asociados a un usuario específico.
     *
     * @param username el nombre de usuario asociado a los mensajes.
     * @return una lista de mensajes cuyo autor coincide con el nombre de usuario proporcionado.
     */
    List<Mensaje> findByUsername(String username);

    /**
     * Obtiene una conversación bidireccional entre dos usuarios.
     *
     * Esta consulta personalizada utiliza `$or` para encontrar mensajes donde el emisor y el destinatario
     * sean intercambiables entre dos usuarios. Permite obtener todas las interacciones entre ellos.
     *
     * @param emisor el nombre del usuario que envía los mensajes.
     * @param destinatario el nombre del usuario que recibe los mensajes.
     * @return una lista de mensajes intercambiados entre el emisor y el destinatario.
     */
    @Query("{ $or: [ " +
            "{ $and: [ { username: ?0 }, { destinatario: ?1 } ] }, " +
            "{ $and: [ { username: ?1 }, { destinatario: ?0 } ] } " +
            "] }")
    List<Mensaje> findConversacion(String emisor, String destinatario);
}

