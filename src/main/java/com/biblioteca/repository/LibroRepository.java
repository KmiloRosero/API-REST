package com.biblioteca.repository;

import com.biblioteca.model.Libro;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Libro.
 *
 * Al extender MongoRepository, Spring genera automáticamente:
 *   Libro save(Libro libro)             → Crear o actualizar un documento
 *   Optional<Libro> findById(String id) → Buscar un documento por su ID
 *   List<Libro> findAll()               → Obtener todos los documentos
 *   void deleteById(String id)          → Eliminar un documento por su ID
 *   boolean existsById(String id)       → Verificar si un documento existe
 *   long count()                        → Contar el total de documentos
 *
 * @param Libro  tipo de documento que maneja este repositorio
 * @param String tipo del campo @Id (ObjectId de MongoDB representado como String)
 */
@Repository
public interface LibroRepository extends MongoRepository<Libro, String> {

}
