package com.biblioteca.dto;

import com.biblioteca.model.Ejemplar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de salida para devolver información de un Ejemplar al cliente.
 * EjemplarResponse → lo que el API DEVUELVE al cliente (con id y tituloLibro)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EjemplarResponse {

    private String id;
    private String codigoEjemplar;
    private String libroId;
    private String tituloLibro;
    private Ejemplar.EstadoEjemplar estado;
    private String ubicacion;
}
