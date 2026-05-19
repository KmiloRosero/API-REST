package com.biblioteca.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entidad que representa a un Usuario del sistema de biblioteca.
 * Puede ser Estudiante, Profesor o Bibliotecario (discriminado por tipoUsuario).
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
    private TipoUsuario tipoUsuario;

    // Campos específicos de Estudiante
    private String codigoEstudiante;
    private String programa;

    // Campos específicos de Profesor
    private String codigoProfesor;
    private String facultad;

    // Campos específicos de Bibliotecario
    private String codigoEmpleado;
    private String turno;

    public enum TipoUsuario {
        ESTUDIANTE,
        PROFESOR,
        BIBLIOTECARIO
    }
}
