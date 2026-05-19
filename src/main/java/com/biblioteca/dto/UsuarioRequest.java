package com.biblioteca.dto;

import com.biblioteca.model.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de entrada para crear o actualizar un Usuario.
 * Aplica validaciones según el tipo de usuario.
 *
 * Patrón DTO: separa la representación de la API de la entidad de base de datos.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe tener un formato válido")
    private String correo;

    private String telefono;

    @NotNull(message = "El tipo de usuario es obligatorio")
    private Usuario.TipoUsuario tipoUsuario;

    // ─── Campos específicos de Estudiante ───
    /** Requerido si tipoUsuario = ESTUDIANTE */
    private String codigoEstudiante;
    private String programa;

    // ─── Campos específicos de Profesor ───
    /** Requerido si tipoUsuario = PROFESOR */
    private String codigoProfesor;
    private String facultad;

    // ─── Campos específicos de Bibliotecario ───
    /** Requerido si tipoUsuario = BIBLIOTECARIO */
    private String codigoEmpleado;
    private String turno;
}
