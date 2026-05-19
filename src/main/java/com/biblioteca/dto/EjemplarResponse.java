package com.biblioteca.dto;

import com.biblioteca.model.Ejemplar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de salida para devolver información de un Ejemplar al cliente.
 * Incluye el título del libro para mayor legibilidad en la respuesta.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EjemplarResponse {

    private String id;
    private String codigoEjemplar;
    private String libroId;

    /** Título del libro incluido para facilitar la lectura de la respuesta */
    private String tituloLibro;

    private Ejemplar.EstadoEjemplar estado;
    private String ubicacion;
}
