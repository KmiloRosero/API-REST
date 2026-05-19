package com.biblioteca.dto;

import com.biblioteca.model.Prestamo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO de salida para devolver información de un Préstamo al cliente.
 * Incluye datos enriquecidos del usuario y del ejemplar para mayor legibilidad.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrestamoResponse {

    private String id;

    // ─── Datos del usuario ───
    private String usuarioId;
    private String nombreUsuario;

    // ─── Datos del ejemplar ───
    private String ejemplarId;
    private String codigoEjemplar;
    private String tituloLibro;

    // ─── Fechas ───
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionEsperada;
    private LocalDate fechaDevolucionReal;

    private Prestamo.EstadoPrestamo estado;
    private String observaciones;

    /** Indica si el préstamo está vencido (fecha esperada < hoy y estado ACTIVO) */
    private boolean vencido;
}
