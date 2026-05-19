package com.biblioteca.dto;

import com.biblioteca.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de salida para devolver información de un Usuario al cliente.
 * Incluye el campo 'id' generado por MongoDB.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {

    private String id;
    private String nombre;
    private String correo;
    private String telefono;
    private Usuario.TipoUsuario tipoUsuario;

    // ─── Campos específicos de Estudiante ───
    private String codigoEstudiante;
    private String programa;

    // ─── Campos específicos de Profesor ───
    private String codigoProfesor;
    private String facultad;

    // ─── Campos específicos de Bibliotecario ───
    private String codigoEmpleado;
    private String turno;
}
