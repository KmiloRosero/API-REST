package com.biblioteca.service;

import com.biblioteca.dto.UsuarioRequest;
import com.biblioteca.dto.UsuarioResponse;
import com.biblioteca.model.Usuario;

import java.util.List;

/**
 * Interfaz que define el contrato de operaciones para la gestión de Usuarios.
 *
 * Patrón POO: Abstracción — define el contrato sin revelar la implementación.
 * Patrón SOLID: Inversión de Dependencias — el Controller depende de esta interfaz.
 */
public interface UsuarioService {

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param request Datos del usuario a registrar
     * @return El usuario registrado con su ID generado
     * @throws IllegalArgumentException si ya existe un usuario con el mismo correo
     */
    UsuarioResponse registrarUsuario(UsuarioRequest request);

    /**
     * Actualiza los datos de un usuario existente.
     *
     * @param id      ID del usuario a actualizar
     * @param request Nuevos datos del usuario
     * @return El usuario actualizado
     * @throws RuntimeException si el usuario no existe
     */
    UsuarioResponse actualizarUsuario(String id, UsuarioRequest request);

    /**
     * Elimina un usuario del sistema.
     *
     * @param id ID del usuario a eliminar
     * @throws RuntimeException si el usuario no existe
     */
    void eliminarUsuario(String id);

    /**
     * Consulta un usuario por su ID.
     *
     * @param id ID del usuario
     * @return El usuario encontrado
     * @throws RuntimeException si el usuario no existe
     */
    UsuarioResponse consultarUsuario(String id);

    /**
     * Lista todos los usuarios del sistema.
     *
     * @return Lista de todos los usuarios
     */
    List<UsuarioResponse> listarUsuarios();

    /**
     * Lista usuarios por tipo.
     *
     * @param tipoUsuario Tipo de usuario a filtrar
     * @return Lista de usuarios del tipo indicado
     */
    List<UsuarioResponse> listarPorTipo(Usuario.TipoUsuario tipoUsuario);

    /**
     * Busca usuarios por nombre (búsqueda parcial).
     *
     * @param nombre Texto a buscar en el nombre
     * @return Lista de usuarios que coinciden
     */
    List<UsuarioResponse> buscarPorNombre(String nombre);
}
