package com.biblioteca.controller;

import com.biblioteca.dto.PrestamoRequest;
import com.biblioteca.dto.PrestamoResponse;
import com.biblioteca.service.PrestamoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de Préstamos.
 * Es el controlador más complejo: coordina usuarios, ejemplares y préstamos.
 *
 * Endpoints disponibles:
 *   POST   /api/prestamos                        → Registrar un préstamo
 *   PUT    /api/prestamos/{id}/devolucion         → Registrar devolución
 *   GET    /api/prestamos                        → Listar todos los préstamos
 *   GET    /api/prestamos/{id}                   → Consultar un préstamo por ID
 *   GET    /api/prestamos/activos                → Listar préstamos activos
 *   GET    /api/prestamos/vencidos               → Listar préstamos vencidos
 *   GET    /api/prestamos/usuario/{usuarioId}    → Listar préstamos de un usuario
 */
@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // POST /api/prestamos — Registrar un préstamo
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Registra un nuevo préstamo.
     * Reglas aplicadas automáticamente:
     *   - El ejemplar debe estar DISPONIBLE.
     *   - El ejemplar cambia a estado PRESTADO.
     *   - La fecha de préstamo se asigna como la fecha actual.
     *
     * @param request Datos del préstamo (usuarioId, ejemplarId, fechaDevolucionEsperada)
     * @return 201 Created con el préstamo registrado
     */
    @PostMapping
    public ResponseEntity<PrestamoResponse> registrarPrestamo(
            @Valid @RequestBody PrestamoRequest request) {
        PrestamoResponse response = prestamoService.registrarPrestamo(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // PUT /api/prestamos/{id}/devolucion — Registrar devolución
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Registra la devolución de un ejemplar prestado.
     * Reglas aplicadas automáticamente:
     *   - El préstamo debe estar en estado ACTIVO.
     *   - El ejemplar vuelve a estado DISPONIBLE.
     *   - La fecha de devolución real se asigna como la fecha actual.
     *   - El estado del préstamo cambia a DEVUELTO.
     *
     * @param id ID del préstamo a devolver
     * @return 200 OK con el préstamo actualizado
     */
    @PutMapping("/{id}/devolucion")
    public ResponseEntity<PrestamoResponse> registrarDevolucion(@PathVariable String id) {
        PrestamoResponse response = prestamoService.registrarDevolucion(id);
        return ResponseEntity.ok(response);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // GET /api/prestamos — Listar todos los préstamos
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Retorna la lista completa de préstamos (activos, devueltos y vencidos).
     *
     * @return 200 OK con la lista de préstamos
     */
    @GetMapping
    public ResponseEntity<List<PrestamoResponse>> listarPrestamos() {
        List<PrestamoResponse> prestamos = prestamoService.listarPrestamos();
        return ResponseEntity.ok(prestamos);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // GET /api/prestamos/{id} — Consultar un préstamo por ID
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Retorna los datos de un préstamo específico.
     *
     * @param id ID del préstamo
     * @return 200 OK con el préstamo
     */
    @GetMapping("/{id}")
    public ResponseEntity<PrestamoResponse> consultarPrestamo(@PathVariable String id) {
        PrestamoResponse response = prestamoService.consultarPrestamo(id);
        return ResponseEntity.ok(response);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // GET /api/prestamos/activos — Listar préstamos activos
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Lista todos los préstamos en estado ACTIVO (ejemplares aún no devueltos).
     *
     * @return 200 OK con la lista de préstamos activos
     */
    @GetMapping("/activos")
    public ResponseEntity<List<PrestamoResponse>> listarPrestamosActivos() {
        List<PrestamoResponse> prestamos = prestamoService.listarPrestamosActivos();
        return ResponseEntity.ok(prestamos);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // GET /api/prestamos/vencidos — Listar préstamos vencidos
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Lista los préstamos cuya fecha de devolución esperada ya pasó
     * y que siguen en estado ACTIVO (no han sido devueltos).
     *
     * @return 200 OK con la lista de préstamos vencidos
     */
    @GetMapping("/vencidos")
    public ResponseEntity<List<PrestamoResponse>> listarPrestamosVencidos() {
        List<PrestamoResponse> prestamos = prestamoService.listarPrestamosVencidos();
        return ResponseEntity.ok(prestamos);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // GET /api/prestamos/usuario/{usuarioId} — Préstamos de un usuario
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Lista todos los préstamos de un usuario específico (historial completo).
     * Ejemplo: GET /api/prestamos/usuario/64a8f2b3c4d5e6f7a8b9c0d1
     *
     * @param usuarioId ID del usuario
     * @return 200 OK con la lista de préstamos del usuario
     */
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PrestamoResponse>> listarPorUsuario(
            @PathVariable String usuarioId) {
        List<PrestamoResponse> prestamos = prestamoService.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(prestamos);
    }
}
