package com.biblioteca.repository;

import com.biblioteca.model.Prestamo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Prestamo.
 * Gestiona el acceso a la colección "prestamos" en MongoDB.
 */
@Repository
public interface PrestamoRepository extends MongoRepository<Prestamo, String> {

    /**
     * Busca todos los préstamos de un usuario específico.
     *
     * @param usuarioId ID del usuario
     * @return Lista de préstamos del usuario
     */
    List<Prestamo> findByUsuarioId(String usuarioId);

    /**
     * Busca todos los préstamos de un ejemplar específico.
     *
     * @param ejemplarId ID del ejemplar
     * @return Lista de préstamos del ejemplar
     */
    List<Prestamo> findByEjemplarId(String ejemplarId);

    /**
     * Busca préstamos por estado (ACTIVO, DEVUELTO, VENCIDO).
     *
     * @param estado Estado del préstamo
     * @return Lista de préstamos en ese estado
     */
    List<Prestamo> findByEstado(Prestamo.EstadoPrestamo estado);

    /**
     * Busca el préstamo activo de un ejemplar específico.
     * Un ejemplar solo puede tener un préstamo ACTIVO a la vez.
     *
     * @param ejemplarId ID del ejemplar
     * @param estado     Estado ACTIVO
     * @return Optional con el préstamo activo si existe
     */
    Optional<Prestamo> findByEjemplarIdAndEstado(String ejemplarId, Prestamo.EstadoPrestamo estado);

    /**
     * Busca préstamos activos de un usuario.
     *
     * @param usuarioId ID del usuario
     * @param estado    Estado ACTIVO
     * @return Lista de préstamos activos del usuario
     */
    List<Prestamo> findByUsuarioIdAndEstado(String usuarioId, Prestamo.EstadoPrestamo estado);

    /**
     * Busca préstamos cuya fecha de devolución esperada sea anterior a la fecha dada
     * y que estén en estado ACTIVO (préstamos vencidos).
     *
     * @param fecha  Fecha de referencia
     * @param estado Estado ACTIVO
     * @return Lista de préstamos vencidos
     */
    List<Prestamo> findByFechaDevolucionEsperadaBeforeAndEstado(LocalDate fecha, Prestamo.EstadoPrestamo estado);
}
