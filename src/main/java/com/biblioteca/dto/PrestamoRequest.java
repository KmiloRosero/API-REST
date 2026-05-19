package com.biblioteca.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO de entrada para crear un Préstamo.
 * El estado se asigna automáticamente como ACTIVO en el Service.
 * La fechaPrestamo se asigna automáticamente como la fecha actual.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrestamoRequest {

    @NotBlank(message = "El ID del usuario es obligatorio")
    private String usuarioId;

    @NotBlank(message = "El ID del ejemplar es obligatorio")
    private String ejemplarId;

    @NotNull(message = "La fecha de devolución esperada es obligatoria")
    @Future(message = "La fecha de devolución debe ser una fecha futura")
    private LocalDate fechaDevolucionEsperada;

    /** Observaciones opcionales del bibliotecario al registrar el préstamo */
    private String observaciones;
}
