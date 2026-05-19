package com.biblioteca.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

/**
 * Entidad que representa el acto de prestar un Ejemplar a un Usuario.
 * Es la entidad más compleja del sistema: relaciona Usuario y Ejemplar.
 *
 * Patrón POO: Asociación — Prestamo referencia a Usuario (usuarioId) y Ejemplar (ejemplarId).
 * Patrón POO: Encapsulamiento — el estado del préstamo está controlado mediante enum.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "prestamos")
public class Prestamo {

    @Id
    private String id;

    /**
     * Referencia al Usuario que tomó el préstamo.
     * Relación: Prestamo N → 1 Usuario
     */
    private String usuarioId;

    /**
     * Referencia al Ejemplar prestado.
     * Relación: Prestamo N → 1 Ejemplar
     */
    private String ejemplarId;

    /** Fecha en que se realizó el préstamo */
    private LocalDate fechaPrestamo;

    /** Fecha límite para devolver el ejemplar */
    private LocalDate fechaDevolucionEsperada;

    /** Fecha real en que se devolvió el ejemplar (null si aún no se devuelve) */
    private LocalDate fechaDevolucionReal;

    /**
     * Estado actual del préstamo.
     * ACTIVO: el ejemplar está en manos del usuario.
     * DEVUELTO: el ejemplar fue devuelto a tiempo.
     * VENCIDO: el ejemplar no fue devuelto en la fecha esperada.
     */
    private EstadoPrestamo estado;

    /** Observaciones adicionales del bibliotecario */
    private String observaciones;

    /**
     * Enum que define los estados posibles de un préstamo.
     */
    public enum EstadoPrestamo {
        ACTIVO,
        DEVUELTO,
        VENCIDO
    }
}
