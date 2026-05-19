package com.biblioteca.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entidad que representa una copia física de un Libro.
 * Un Libro puede tener múltiples Ejemplares; cada Ejemplar pertenece a un solo Libro.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "ejemplares")
public class Ejemplar {

    @Id
    private String id;

    private String codigoEjemplar;

    /** ID del Libro al que pertenece este ejemplar */
    private String libroId;

    /**
     * Estado del ejemplar:
     *   DISPONIBLE   → puede prestarse
     *   PRESTADO     → actualmente en manos de un usuario
     *   EN_REPARACION → no disponible temporalmente
     *   DADO_DE_BAJA  → retirado del sistema
     */
    private EstadoEjemplar estado;

    private String ubicacion;

    public enum EstadoEjemplar {
        DISPONIBLE,
        PRESTADO,
        EN_REPARACION,
        DADO_DE_BAJA
    }
}
