package com.biblioteca.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de salida para devolver información de un Libro al cliente.
 * Incluye el campo 'id' generado por MongoDB.
 *
 * LibroResponse → lo que el API DEVUELVE al cliente (con id)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroResponse {

    private String id;         // ← LibroResponse SÍ incluye el id
    private String isbn;
    private String titulo;
    private String autor;
    private int anioPublicacion;
    private String categoria;
}
