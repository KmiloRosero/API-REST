package com.biblioteca.dto;

import com.biblioteca.model.Prestamo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO de salida para devolver información de un Préstamo al cliente.
 * PrestamoResponse → lo que el API DEVUELVE al cliente (con id y datos enriquecidos)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrestamoResponse {

    private String id;

    private String usuarioId;
    private String nombreUsuario;

    private String ejemplarId;
    private String codigoEjemplar;
    private String tituloLibro;

    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionEsperada;
    private LocalDate fechaDevolucionReal;

    private Prestamo.EstadoPrestamo estado;
}
