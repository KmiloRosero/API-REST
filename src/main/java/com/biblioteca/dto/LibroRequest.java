package com.biblioteca.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de entrada para crear o actualizar un Libro.
 * No incluye el campo 'id' porque MongoDB lo genera automáticamente.
 *
 * Patrón DTO: separa la representación de la API de la entidad de base de datos.
 * Incluye validaciones con Bean Validation para garantizar datos correctos.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroRequest {

    @NotBlank(message = "El ISBN es obligatorio")
    private String isbn;

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @NotBlank(message = "El autor es obligatorio")
    private String autor;

    @NotNull(message = "El año de publicación es obligatorio")
    @Min(value = 1000, message = "El año de publicación debe ser válido")
    private Integer anioPublicacion;

    @NotBlank(message = "La categoría es obligatoria")
    private String categoria;

    /** Descripción opcional del libro */
    private String descripcion;

    /** Editorial opcional */
    private String editorial;
}
