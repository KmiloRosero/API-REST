package com.biblioteca.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entidad que representa una obra intelectual en el sistema de biblioteca.
 *
 * @Document(collection = "libros") → guarda en la colección "libros" de MongoDB
 * @Id                              → campo identificador único (ObjectId de MongoDB)
 * @Data                            → Lombok genera getters, setters, toString, equals, hashCode
 * @NoArgsConstructor               → constructor sin argumentos (requerido por Spring)
 * @AllArgsConstructor              → constructor con todos los argumentos
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "libros")
public class Libro {

    @Id
    private String id;

    private String isbn;
    private String titulo;
    private String autor;
    private int anioPublicacion;
    private String categoria;
}
