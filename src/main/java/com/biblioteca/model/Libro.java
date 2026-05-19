package com.biblioteca.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entidad que representa una obra intelectual en el sistema de biblioteca.
 * Un Libro puede tener múltiples Ejemplares (copias físicas).
 *
 * Patrón POO: Encapsulamiento — todos los atributos son privados con acceso
 * controlado mediante getters/setters generados por Lombok (@Data).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "libros")
public class Libro {

    /**
     * Identificador único generado automáticamente por MongoDB (ObjectId de 24 chars).
     * Ejemplo: "64a8f2b3c4d5e6f7a8b9c0d1"
     */
    @Id
    private String id;

    /** ISBN: International Standard Book Number — identificador único de la obra */
    private String isbn;

    private String titulo;
    private String autor;
    private int anioPublicacion;
    private String categoria;

    /** Descripción opcional del libro */
    private String descripcion;

    /** Editorial que publicó el libro */
    private String editorial;
}
