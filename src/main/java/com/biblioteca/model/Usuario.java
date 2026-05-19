package com.biblioteca.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Clase base que representa a un Usuario del sistema de biblioteca.
 * Aplica herencia: Estudiante, Profesor y Bibliotecario extienden esta clase.
 *
 * Patrón POO: Herencia — esta clase es la superclase de todos los tipos de usuario.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "usuarios")
public class Usuario {

    @Id
    private String id;

    private String nombre;
    private String correo;
    private String telefono;

    /**
     * Tipo de usuario: ESTUDIANTE, PROFESOR, BIBLIOTECARIO
     * Permite discriminar el tipo sin necesidad de colecciones separadas.
     */
    private TipoUsuario tipoUsuario;

    // ─── Campos específicos de Estudiante ───
    private String codigoEstudiante;
    private String programa;

    // ─── Campos específicos de Profesor ───
    private String codigoProfesor;
    private String facultad;

    // ─── Campos específicos de Bibliotecario ───
    private String codigoEmpleado;
    private String turno;

    /**
     * Enum que define los tipos de usuario del sistema.
     * Aplica POO: encapsulamiento de los valores válidos.
     */
    public enum TipoUsuario {
        ESTUDIANTE,
        PROFESOR,
        BIBLIOTECARIO
    }
}
