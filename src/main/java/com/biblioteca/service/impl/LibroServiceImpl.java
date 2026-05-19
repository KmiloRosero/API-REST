package com.biblioteca.service.impl;

import com.biblioteca.dto.LibroRequest;
import com.biblioteca.dto.LibroResponse;
import com.biblioteca.model.Libro;
import com.biblioteca.repository.LibroRepository;
import com.biblioteca.service.LibroService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación concreta de LibroService.
 * Contiene toda la lógica de negocio para la gestión de libros.
 *
 * Patrón POO: Polimorfismo — implementa la interfaz LibroService.
 * Patrón SOLID: Responsabilidad Única — solo gestiona la lógica de libros.
 * Patrón SOLID: Inversión de Dependencias — depende de la abstracción LibroRepository.
 *
 * @Service indica a Spring que esta clase es un componente de servicio
 * y la registra en el contenedor de inyección de dependencias.
 */
@Service
public class LibroServiceImpl implements LibroService {

    private final LibroRepository libroRepository;

    /**
     * Inyección por constructor (patrón recomendado en Spring Boot).
     * Spring detecta automáticamente que necesita un LibroRepository y lo provee.
     *
     * @param libroRepository Repositorio de libros inyectado por Spring
     */
    public LibroServiceImpl(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // CREAR
    // ─────────────────────────────────────────────────────────────────────────

    @Override
    public LibroResponse crearLibro(LibroRequest request) {
        // Regla de negocio: no puede existir otro libro con el mismo ISBN
        if (libroRepository.existsByIsbn(request.getIsbn())) {
            throw new IllegalArgumentException(
                    "Ya existe un libro con el ISBN: " + request.getIsbn());
        }

        // Mapear Request → Entidad
        Libro libro = mapToEntity(request);

        // Persistir en MongoDB (save() retorna el objeto con el ID generado)
        Libro libroGuardado = libroRepository.save(libro);

        // Mapear Entidad → Response y retornar
        return mapToResponse(libroGuardado);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // ACTUALIZAR
    // ─────────────────────────────────────────────────────────────────────────

    @Override
    public LibroResponse actualizarLibro(String id, LibroRequest request) {
        // Buscar el libro existente; lanzar excepción si no existe
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con id: " + id));

        // Actualizar los campos con los nuevos datos
        libro.setIsbn(request.getIsbn());
        libro.setTitulo(request.getTitulo());
        libro.setAutor(request.getAutor());
        libro.setAnioPublicacion(request.getAnioPublicacion());
        libro.setCategoria(request.getCategoria());
        libro.setDescripcion(request.getDescripcion());
        libro.setEditorial(request.getEditorial());

        // Guardar los cambios en MongoDB
        Libro libroActualizado = libroRepository.save(libro);

        return mapToResponse(libroActualizado);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // ELIMINAR
    // ─────────────────────────────────────────────────────────────────────────

    @Override
    public void eliminarLibro(String id) {
        // Verificar que el libro exista antes de eliminar
        if (!libroRepository.existsById(id)) {
            throw new RuntimeException("Libro no encontrado con id: " + id);
        }
        libroRepository.deleteById(id);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // CONSULTAR
    // ─────────────────────────────────────────────────────────────────────────

    @Override
    public LibroResponse consultarLibro(String id) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con id: " + id));
        return mapToResponse(libro);
    }

    @Override
    public List<LibroResponse> listarLibros() {
        // Java Streams: transforma cada Libro en LibroResponse
        return libroRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<LibroResponse> buscarPorTitulo(String titulo) {
        return libroRepository.findByTituloContainingIgnoreCase(titulo)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<LibroResponse> buscarPorAutor(String autor) {
        return libroRepository.findByAutorContainingIgnoreCase(autor)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<LibroResponse> buscarPorCategoria(String categoria) {
        return libroRepository.findByCategoria(categoria)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // MÉTODOS AUXILIARES DE MAPEO (privados)
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Convierte un LibroRequest (DTO de entrada) en una entidad Libro.
     * Aplica POO: encapsulamiento de la lógica de conversión.
     */
    private Libro mapToEntity(LibroRequest request) {
        Libro libro = new Libro();
        libro.setIsbn(request.getIsbn());
        libro.setTitulo(request.getTitulo());
        libro.setAutor(request.getAutor());
        libro.setAnioPublicacion(request.getAnioPublicacion());
        libro.setCategoria(request.getCategoria());
        libro.setDescripcion(request.getDescripcion());
        libro.setEditorial(request.getEditorial());
        return libro;
    }

    /**
     * Convierte una entidad Libro en un LibroResponse (DTO de salida).
     * Mantiene la separación entre la capa de datos y la capa de presentación.
     */
    private LibroResponse mapToResponse(Libro libro) {
        return new LibroResponse(
                libro.getId(),
                libro.getIsbn(),
                libro.getTitulo(),
                libro.getAutor(),
                libro.getAnioPublicacion(),
                libro.getCategoria(),
                libro.getDescripcion(),
                libro.getEditorial()
        );
    }
}
