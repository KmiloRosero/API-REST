package com.biblioteca.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de entrada para crear o actualizar un Libro.
 * No incluye el campo 'id' porque MongoDB lo genera automáticamente.
 *
 * LibroRequest → lo que el cliente ENVÍA al API (sin id)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroRequest {

    private String isbn;
    private String titulo;
    private String autor;
    private int anioPublicacion;
    private String categoria;
}
