package com.biblioteca.controller;

import com.biblioteca.dto.EjemplarRequest;
import com.biblioteca.dto.EjemplarResponse;
import com.biblioteca.model.Ejemplar;
import com.biblioteca.service.EjemplarService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de Ejemplares (copias físicas de libros).
 *
 * Endpoints disponibles:
 *   POST   /api/ejemplares                        → Registrar un ejemplar
 *   GET    /api/ejemplares                        → Listar todos los ejemplares
 *   GET    /api/ejemplares/{id}                   → Consultar un ejemplar por ID
 *   PUT    /api/ejemplares/{id}                   → Actualizar un ejemplar
 *   DELETE /api/ejemplares/{id}                   → Eliminar un ejemplar
 *   GET    /api/ejemplares/libro/{libroId}        → Listar ejemplares de un libro
 *   GET    /api/ejemplares/libro/{libroId}/disponibles → Listar disponibles de un libro
 *   GET    /api/ejemplares/estado/{estado}        → Listar por estado
 */
@RestController
@RequestMapping("/api/ejemplares")
public class EjemplarController {

    private final EjemplarService ejemplarService;

    public EjemplarController(EjemplarService ejemplarService) {
        this.ejemplarService = ejemplarService;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // POST /api/ejemplares — Registrar un ejemplar
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Registra una nueva copia física de un libro.
     *
     * @param request Datos del ejemplar
     * @return 201 Created con el ejemplar registrado
     */
    @PostMapping
    public ResponseEntity<EjemplarResponse> registrarEjemplar(
            @Valid @RequestBody EjemplarRequest request) {
        EjemplarResponse response = ejemplarService.registrarEjemplar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // GET /api/ejemplares — Listar todos los ejemplares
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Retorna la lista completa de ejemplares.
     *
     * @return 200 OK con la lista de ejemplares
     */
    @GetMapping
    public ResponseEntity<List<EjemplarResponse>> listarEjemplares() {
        List<EjemplarResponse> ejemplares = ejemplarService.listarEjemplares();
        return ResponseEntity.ok(ejemplares);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // GET /api/ejemplares/{id} — Consultar un ejemplar por ID
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Retorna los datos de un ejemplar específico.
     *
     * @param id ID del ejemplar
     * @return 200 OK con el ejemplar
     */
    @GetMapping("/{id}")
    public ResponseEntity<EjemplarResponse> consultarEjemplar(@PathVariable String id) {
        EjemplarResponse response = ejemplarService.consultarEjemplar(id);
        return ResponseEntity.ok(response);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // PUT /api/ejemplares/{id} — Actualizar un ejemplar
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Actualiza los datos de un ejemplar existente.
     *
     * @param id      ID del ejemplar a actualizar
     * @param request Nuevos datos del ejemplar
     * @return 200 OK con el ejemplar actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<EjemplarResponse> actualizarEjemplar(
            @PathVariable String id,
            @Valid @RequestBody EjemplarRequest request) {
        EjemplarResponse response = ejemplarService.actualizarEjemplar(id, request);
        return ResponseEntity.ok(response);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // DELETE /api/ejemplares/{id} — Eliminar un ejemplar
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Elimina un ejemplar del sistema.
     *
     * @param id ID del ejemplar a eliminar
     * @return 204 No Content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEjemplar(@PathVariable String id) {
        ejemplarService.eliminarEjemplar(id);
        return ResponseEntity.noContent().build();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // GET /api/ejemplares/libro/{libroId} — Listar ejemplares de un libro
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Lista todos los ejemplares de un libro específico.
     * Ejemplo: GET /api/ejemplares/libro/64a8f2b3c4d5e6f7a8b9c0d1
     *
     * @param libroId ID del libro
     * @return 200 OK con la lista de ejemplares del libro
     */
    @GetMapping("/libro/{libroId}")
    public ResponseEntity<List<EjemplarResponse>> listarPorLibro(@PathVariable String libroId) {
        List<EjemplarResponse> ejemplares = ejemplarService.listarPorLibro(libroId);
        return ResponseEntity.ok(ejemplares);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // GET /api/ejemplares/libro/{libroId}/disponibles — Disponibles de un libro
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Lista los ejemplares DISPONIBLES de un libro específico.
     * Útil para saber si se puede realizar un préstamo.
     *
     * @param libroId ID del libro
     * @return 200 OK con la lista de ejemplares disponibles
     */
    @GetMapping("/libro/{libroId}/disponibles")
    public ResponseEntity<List<EjemplarResponse>> listarDisponiblesPorLibro(
            @PathVariable String libroId) {
        List<EjemplarResponse> ejemplares = ejemplarService.listarDisponiblesPorLibro(libroId);
        return ResponseEntity.ok(ejemplares);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // GET /api/ejemplares/estado/{estado} — Listar por estado
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Lista ejemplares filtrados por estado.
     * Ejemplo: GET /api/ejemplares/estado/DISPONIBLE
     *          GET /api/ejemplares/estado/PRESTADO
     *
     * @param estado Estado del ejemplar: DISPONIBLE, PRESTADO, EN_REPARACION, DADO_DE_BAJA
     * @return 200 OK con la lista de ejemplares en ese estado
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<EjemplarResponse>> listarPorEstado(
            @PathVariable Ejemplar.EstadoEjemplar estado) {
        List<EjemplarResponse> ejemplares = ejemplarService.listarPorEstado(estado);
        return ResponseEntity.ok(ejemplares);
    }
}
