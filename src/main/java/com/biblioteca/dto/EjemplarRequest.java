package com.biblioteca.dto;

import com.biblioteca.model.Ejemplar;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de entrada para crear o actualizar un Ejemplar.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EjemplarRequest {

    @NotBlank(message = "El código del ejemplar es obligatorio")
    private String codigoEjemplar;

    @NotBlank(message = "El ID del libro es obligatorio")
    private String libroId;

    @NotNull(message = "El estado del ejemplar es obligatorio")
    private Ejemplar.EstadoEjemplar estado;

    @NotBlank(message = "La ubicación es obligatoria")
    private String ubicacion;
}
