package com.biblioteca.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO de entrada para registrar un Préstamo.
 * PrestamoRequest → lo que el cliente ENVÍA al API (sin id)
 *
 * El estado se asigna automáticamente como ACTIVO.
 * La fechaPrestamo se asigna automáticamente como la fecha actual.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrestamoRequest {

    private String usuarioId;
    private String ejemplarId;
    private LocalDate fechaDevolucionEsperada;
}
