package com.biblioteca.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de salida para devolver información de un Libro al cliente.
 * Incluye el campo 'id' generado por MongoDB.
 *
 * Patrón DTO: el cliente recibe solo los datos que necesita ver,
 * sin exponer detalles internos de la base de datos.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroResponse {

    /** ID único generado por MongoDB */
    private String id;

    private String isbn;
    private String titulo;
    private String autor;
    private int anioPublicacion;
    private String categoria;
    private String descripcion;
    private String editorial;
}
