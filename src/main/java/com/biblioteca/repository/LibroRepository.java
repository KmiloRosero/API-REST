package com.biblioteca.repository;

import com.biblioteca.model.Libro;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Libro.
 * Extiende MongoRepository para obtener automáticamente las operaciones CRUD básicas:
 *   save(), findById(), findAll(), deleteById(), existsById(), count()
 *
 * Spring Data MongoDB genera la implementación en tiempo de ejecución.
 *
 * @param Libro  — tipo de documento que maneja este repositorio
 * @param String — tipo del campo @Id (ObjectId de MongoDB representado como String)
 */
@Repository
public interface LibroRepository extends MongoRepository<Libro, String> {

    /**
     * Busca un libro por su ISBN.
     * Spring Data genera la consulta automáticamente a partir del nombre del método.
     *
     * @param isbn ISBN del libro a buscar
     * @return Optional con el libro si existe, vacío si no
     */
    Optional<Libro> findByIsbn(String isbn);

    /**
     * Busca libros cuyo título contenga el texto indicado (búsqueda parcial, sin distinción de mayúsculas).
     *
     * @param titulo Texto a buscar en el título
     * @return Lista de libros que coinciden
     */
    List<Libro> findByTituloContainingIgnoreCase(String titulo);

    /**
     * Busca libros por autor (búsqueda parcial, sin distinción de mayúsculas).
     *
     * @param autor Nombre del autor a buscar
     * @return Lista de libros del autor
     */
    List<Libro> findByAutorContainingIgnoreCase(String autor);

    /**
     * Busca libros por categoría exacta.
     *
     * @param categoria Categoría a filtrar
     * @return Lista de libros de esa categoría
     */
    List<Libro> findByCategoria(String categoria);

    /**
     * Verifica si ya existe un libro con el ISBN dado.
     *
     * @param isbn ISBN a verificar
     * @return true si ya existe, false si no
     */
    boolean existsByIsbn(String isbn);
}
