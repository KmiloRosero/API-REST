package com.biblioteca.controller;

import com.biblioteca.dto.LibroRequest;
import com.biblioteca.dto.LibroResponse;
import com.biblioteca.service.LibroService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de Libros.
 * Define todos los endpoints HTTP del recurso /api/libros.
 *
 * Endpoints disponibles:
 *   POST   /api/libros              → Crear un libro
 *   GET    /api/libros              → Listar todos los libros
 *   GET    /api/libros/{id}         → Consultar un libro por ID
 *   PUT    /api/libros/{id}         → Actualizar un libro
 *   DELETE /api/libros/{id}         → Eliminar un libro
 *   GET    /api/libros/buscar/titulo?q= → Buscar por título
 *   GET    /api/libros/buscar/autor?q=  → Buscar por autor
 *   GET    /api/libros/buscar/categoria?q= → Buscar por categoría
 *
 * Patrón POO: Delegación — el Controller delega toda la lógica al Service.
 */
@RestController
@RequestMapping("/api/libros")
public class LibroController {

    private final LibroService libroService;

    /**
     * Inyección por constructor del servicio.
     * El Controller depende de la interfaz LibroService, no de la implementación.
     */
    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // POST /api/libros — Crear un libro
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Crea un nuevo libro en el sistema.
     *
     * @param request Datos del libro (validados con @Valid)
     * @return 201 Created con el libro creado (incluye el ID generado)
     */
    @PostMapping
    public ResponseEntity<LibroResponse> crearLibro(@Valid @RequestBody LibroRequest request) {
        LibroResponse response = libroService.crearLibro(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // GET /api/libros — Listar todos los libros
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Retorna la lista completa de libros registrados.
     *
     * @return 200 OK con la lista de libros
     */
    @GetMapping
    public ResponseEntity<List<LibroResponse>> listarLibros() {
        List<LibroResponse> libros = libroService.listarLibros();
        return ResponseEntity.ok(libros);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // GET /api/libros/{id} — Consultar un libro por ID
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Retorna los datos de un libro específico.
     *
     * @param id ID del libro (ObjectId de MongoDB)
     * @return 200 OK con el libro, o 404 si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<LibroResponse> consultarLibro(@PathVariable String id) {
        LibroResponse response = libroService.consultarLibro(id);
        return ResponseEntity.ok(response);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // PUT /api/libros/{id} — Actualizar un libro
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Actualiza todos los datos de un libro existente.
     *
     * @param id      ID del libro a actualizar
     * @param request Nuevos datos del libro
     * @return 200 OK con el libro actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<LibroResponse> actualizarLibro(
            @PathVariable String id,
            @Valid @RequestBody LibroRequest request) {
        LibroResponse response = libroService.actualizarLibro(id, request);
        return ResponseEntity.ok(response);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // DELETE /api/libros/{id} — Eliminar un libro
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Elimina un libro del sistema.
     *
     * @param id ID del libro a eliminar
     * @return 204 No Content (sin cuerpo en la respuesta)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLibro(@PathVariable String id) {
        libroService.eliminarLibro(id);
        return ResponseEntity.noContent().build();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // GET /api/libros/buscar/titulo?q= — Buscar por título
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Busca libros cuyo título contenga el texto indicado.
     * Ejemplo: GET /api/libros/buscar/titulo?q=clean
     *
     * @param q Texto a buscar en el título
     * @return 200 OK con la lista de libros que coinciden
     */
    @GetMapping("/buscar/titulo")
    public ResponseEntity<List<LibroResponse>> buscarPorTitulo(@RequestParam String q) {
        List<LibroResponse> libros = libroService.buscarPorTitulo(q);
        return ResponseEntity.ok(libros);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // GET /api/libros/buscar/autor?q= — Buscar por autor
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Busca libros por nombre de autor.
     * Ejemplo: GET /api/libros/buscar/autor?q=martin
     *
     * @param q Nombre del autor a buscar
     * @return 200 OK con la lista de libros del autor
     */
    @GetMapping("/buscar/autor")
    public ResponseEntity<List<LibroResponse>> buscarPorAutor(@RequestParam String q) {
        List<LibroResponse> libros = libroService.buscarPorAutor(q);
        return ResponseEntity.ok(libros);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // GET /api/libros/buscar/categoria?q= — Buscar por categoría
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Busca libros por categoría exacta.
     * Ejemplo: GET /api/libros/buscar/categoria?q=Programación
     *
     * @param q Categoría a filtrar
     * @return 200 OK con la lista de libros de esa categoría
     */
    @GetMapping("/buscar/categoria")
    public ResponseEntity<List<LibroResponse>> buscarPorCategoria(@RequestParam String q) {
        List<LibroResponse> libros = libroService.buscarPorCategoria(q);
        return ResponseEntity.ok(libros);
    }
}
