package com.biblioteca.dto;

import com.biblioteca.model.Ejemplar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de entrada para crear o actualizar un Ejemplar.
 * EjemplarRequest → lo que el cliente ENVÍA al API (sin id)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EjemplarRequest {

    private String codigoEjemplar;
    private String libroId;
    private Ejemplar.EstadoEjemplar estado;
    private String ubicacion;
}
