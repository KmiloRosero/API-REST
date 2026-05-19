package com.biblioteca.repository;

import com.biblioteca.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Usuario.
 * Spring Data MongoDB genera automáticamente la implementación de todos los métodos.
 */
@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {

    Optional<Usuario> findByCorreo(String correo);

    List<Usuario> findByTipoUsuario(Usuario.TipoUsuario tipoUsuario);

    boolean existsByCorreo(String correo);
}
