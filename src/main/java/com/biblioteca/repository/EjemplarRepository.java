package com.biblioteca.repository;

import com.biblioteca.model.Ejemplar;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Ejemplar.
 * Gestiona el acceso a la colección "ejemplares" en MongoDB.
 */
@Repository
public interface EjemplarRepository extends MongoRepository<Ejemplar, String> {

    /**
     * Busca un ejemplar por su código físico único.
     *
     * @param codigoEjemplar Código del ejemplar
     * @return Optional con el ejemplar si existe
     */
    Optional<Ejemplar> findByCodigoEjemplar(String codigoEjemplar);

    /**
     * Busca todos los ejemplares de un libro específico.
     *
     * @param libroId ID del libro
     * @return Lista de ejemplares del libro
     */
    List<Ejemplar> findByLibroId(String libroId);

    /**
     * Busca ejemplares de un libro que estén en un estado específico.
     *
     * @param libroId ID del libro
     * @param estado  Estado del ejemplar a filtrar
     * @return Lista de ejemplares que coinciden
     */
    List<Ejemplar> findByLibroIdAndEstado(String libroId, Ejemplar.EstadoEjemplar estado);

    /**
     * Busca todos los ejemplares en un estado específico.
     *
     * @param estado Estado a filtrar
     * @return Lista de ejemplares en ese estado
     */
    List<Ejemplar> findByEstado(Ejemplar.EstadoEjemplar estado);

    /**
     * Verifica si ya existe un ejemplar con el código dado.
     *
     * @param codigoEjemplar Código a verificar
     * @return true si ya existe, false si no
     */
    boolean existsByCodigoEjemplar(String codigoEjemplar);
}
