package com.biblioteca.repository;

import com.biblioteca.model.Prestamo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Prestamo.
 * Spring Data MongoDB genera automáticamente la implementación de todos los métodos.
 */
@Repository
public interface PrestamoRepository extends MongoRepository<Prestamo, String> {

    List<Prestamo> findByUsuarioId(String usuarioId);

    List<Prestamo> findByEstado(Prestamo.EstadoPrestamo estado);

    Optional<Prestamo> findByEjemplarIdAndEstado(String ejemplarId, Prestamo.EstadoPrestamo estado);

    List<Prestamo> findByFechaDevolucionEsperadaBeforeAndEstado(
            LocalDate fecha, Prestamo.EstadoPrestamo estado);
}
