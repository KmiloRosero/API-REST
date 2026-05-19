package com.biblioteca.dto;

import com.biblioteca.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de entrada para crear o actualizar un Usuario.
 * UsuarioRequest → lo que el cliente ENVÍA al API (sin id)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequest {

    private String nombre;
    private String correo;
    private Usuario.TipoUsuario tipoUsuario;

    // Campos específicos de Estudiante
    private String codigoEstudiante;
    private String programa;

    // Campos específicos de Profesor
    private String codigoProfesor;
    private String facultad;

    // Campos específicos de Bibliotecario
    private String codigoEmpleado;
    private String turno;
}
