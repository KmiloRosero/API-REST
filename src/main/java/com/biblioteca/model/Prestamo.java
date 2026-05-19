package com.biblioteca.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

/**
 * Entidad que representa el acto de prestar un Ejemplar a un Usuario.
 *
 * Campos requeridos por el tutorial:
 *   usuarioId              → referencia al Usuario
 *   ejemplarId             → referencia al Ejemplar
 *   fechaPrestamo          → fecha en que se realizó el préstamo
 *   fechaDevolucionEsperada → fecha límite para devolver
 *   estado                 → ACTIVO, DEVUELTO, VENCIDO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "prestamos")
public class Prestamo {

    @Id
    private String id;

    private String usuarioId;
    private String ejemplarId;

    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionEsperada;

    /** Fecha real de devolución (null mientras el préstamo esté ACTIVO) */
    private LocalDate fechaDevolucionReal;

    private EstadoPrestamo estado;

    public enum EstadoPrestamo {
        ACTIVO,
        DEVUELTO,
        VENCIDO
    }
}
