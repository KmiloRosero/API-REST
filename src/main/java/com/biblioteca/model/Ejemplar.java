package com.biblioteca.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entidad que representa una copia física de un Libro.
 * Un Libro puede tener múltiples Ejemplares; cada Ejemplar pertenece a un solo Libro.
 *
 * Patrón POO: Asociación — Ejemplar referencia a Libro mediante libroId.
 * Patrón POO: Encapsulamiento — estado del ejemplar controlado mediante enum.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "ejemplares")
public class Ejemplar {

    @Id
    private String id;

    /** Código único físico del ejemplar (ej: "LIB-001-A") */
    private String codigoEjemplar;

    /**
     * Referencia al Libro al que pertenece este ejemplar.
     * Relación: Ejemplar N → 1 Libro
     */
    private String libroId;

    /**
     * Estado actual del ejemplar.
     * DISPONIBLE: puede prestarse.
     * PRESTADO: actualmente en manos de un usuario.
     * EN_REPARACION: no disponible temporalmente.
     * DADO_DE_BAJA: retirado del sistema.
     */
    private EstadoEjemplar estado;

    /** Ubicación física en la biblioteca (ej: "Estante A, Fila 3") */
    private String ubicacion;

    /**
     * Enum que define los estados posibles de un ejemplar.
     * Aplica POO: encapsulamiento de los valores válidos del dominio.
     */
    public enum EstadoEjemplar {
        DISPONIBLE,
        PRESTADO,
        EN_REPARACION,
        DADO_DE_BAJA
    }
}
