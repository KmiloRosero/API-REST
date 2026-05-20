package com.biblioteca.service;

import com.biblioteca.dto.UsuarioRequest;
import com.biblioteca.dto.UsuarioResponse;
import com.biblioteca.model.Usuario;

import java.util.List;

/**
 * Interfaz que define el contrato de operaciones para la gestión de Usuarios.
 * El Controller solo conoce esta interfaz, no la implementación concreta.
 */
public interface UsuarioService {

    UsuarioResponse registrarUsuario(UsuarioRequest request);

    UsuarioResponse actualizarUsuario(String id, UsuarioRequest request);

    void eliminarUsuario(String id);

    UsuarioResponse consultarUsuario(String id);

    List<UsuarioResponse> listarUsuarios();

    List<UsuarioResponse> listarPorTipo(Usuario.TipoUsuario tipoUsuario);

    List<UsuarioResponse> buscarPorNombre(String nombre);
}
