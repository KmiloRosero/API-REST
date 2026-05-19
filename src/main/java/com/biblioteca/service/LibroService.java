package com.biblioteca.service;

import com.biblioteca.dto.LibroRequest;
import com.biblioteca.dto.LibroResponse;

import java.util.List;

/**
 * Interfaz que define el contrato de operaciones para la gestión de Libros.
 *
 * Patrón POO: Abstracción — define QUÉ operaciones existen sin especificar CÓMO se implementan.
 * Patrón SOLID: Principio de Inversión de Dependencias — el Controller depende de esta
 * interfaz, no de la implementación concreta (LibroServiceImpl).
 */
public interface LibroService {

    /**
     * Crea un nuevo libro en el sistema.
     *
     * @param request Datos del libro a crear
     * @return El libro creado con su ID generado
     * @throws IllegalArgumentException si ya existe un libro con el mismo ISBN
     */
    LibroResponse crearLibro(LibroRequest request);

    /**
     * Actualiza los datos de un libro existente.
     *
     * @param id      ID del libro a actualizar
     * @param request Nuevos datos del libro
     * @return El libro actualizado
     * @throws RuntimeException si el libro no existe
     */
    LibroResponse actualizarLibro(String id, LibroRequest request);

    /**
     * Elimina un libro del sistema.
     *
     * @param id ID del libro a eliminar
     * @throws RuntimeException si el libro no existe
     */
    void eliminarLibro(String id);

    /**
     * Consulta un libro por su ID.
     *
     * @param id ID del libro
     * @return El libro encontrado
     * @throws RuntimeException si el libro no existe
     */
    LibroResponse consultarLibro(String id);

    /**
     * Lista todos los libros del sistema.
     *
     * @return Lista de todos los libros
     */
    List<LibroResponse> listarLibros();

    /**
     * Busca libros por título (búsqueda parcial).
     *
     * @param titulo Texto a buscar en el título
     * @return Lista de libros que coinciden
     */
    List<LibroResponse> buscarPorTitulo(String titulo);

    /**
     * Busca libros por autor (búsqueda parcial).
     *
     * @param autor Nombre del autor
     * @return Lista de libros del autor
     */
    List<LibroResponse> buscarPorAutor(String autor);

    /**
     * Busca libros por categoría.
     *
     * @param categoria Categoría a filtrar
     * @return Lista de libros de esa categoría
     */
    List<LibroResponse> buscarPorCategoria(String categoria);
}
