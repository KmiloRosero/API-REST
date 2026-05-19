package com.biblioteca.repository;

import com.biblioteca.model.Ejemplar;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Ejemplar.
 * Spring Data MongoDB genera automáticamente la implementación de todos los métodos.
 */
@Repository
public interface EjemplarRepository extends MongoRepository<Ejemplar, String> {

    List<Ejemplar> findByLibroId(String libroId);

    List<Ejemplar> findByLibroIdAndEstado(String libroId, Ejemplar.EstadoEjemplar estado);

    List<Ejemplar> findByEstado(Ejemplar.EstadoEjemplar estado);

    Optional<Ejemplar> findByCodigoEjemplar(String codigoEjemplar);

    boolean existsByCodigoEjemplar(String codigoEjemplar);
}
