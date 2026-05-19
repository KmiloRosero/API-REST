package com.biblioteca.repository;

import com.biblioteca.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Usuario.
 * Gestiona el acceso a la colección "usuarios" en MongoDB.
 *
 * Spring Data MongoDB genera automáticamente la implementación de todos los métodos.
 */
@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {

    /**
     * Busca un usuario por su correo electrónico.
     *
     * @param correo Correo del usuario
     * @return Optional con el usuario si existe
     */
    Optional<Usuario> findByCorreo(String correo);

    /**
     * Busca usuarios por tipo (ESTUDIANTE, PROFESOR, BIBLIOTECARIO).
     *
     * @param tipoUsuario Tipo de usuario a filtrar
     * @return Lista de usuarios del tipo indicado
     */
    List<Usuario> findByTipoUsuario(Usuario.TipoUsuario tipoUsuario);

    /**
     * Busca usuarios cuyo nombre contenga el texto indicado.
     *
     * @param nombre Texto a buscar en el nombre
     * @return Lista de usuarios que coinciden
     */
    List<Usuario> findByNombreContainingIgnoreCase(String nombre);

    /**
     * Busca un estudiante por su código de estudiante.
     *
     * @param codigoEstudiante Código del estudiante
     * @return Optional con el usuario si existe
     */
    Optional<Usuario> findByCodigoEstudiante(String codigoEstudiante);

    /**
     * Busca un profesor por su código de profesor.
     *
     * @param codigoProfesor Código del profesor
     * @return Optional con el usuario si existe
     */
    Optional<Usuario> findByCodigoProfesor(String codigoProfesor);

    /**
     * Verifica si ya existe un usuario con el correo dado.
     *
     * @param correo Correo a verificar
     * @return true si ya existe, false si no
     */
    boolean existsByCorreo(String correo);
}
