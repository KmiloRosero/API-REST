package com.biblioteca.controller;

import com.biblioteca.dto.UsuarioRequest;
import com.biblioteca.dto.UsuarioResponse;
import com.biblioteca.model.Usuario;
import com.biblioteca.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de Usuarios.
 * Maneja Estudiantes, Profesores y Bibliotecarios bajo el mismo recurso.
 *
 * Endpoints disponibles:
 *   POST   /api/usuarios                    → Registrar un usuario
 *   GET    /api/usuarios                    → Listar todos los usuarios
 *   GET    /api/usuarios/{id}               → Consultar un usuario por ID
 *   PUT    /api/usuarios/{id}               → Actualizar un usuario
 *   DELETE /api/usuarios/{id}               → Eliminar un usuario
 *   GET    /api/usuarios/tipo/{tipo}        → Listar por tipo (ESTUDIANTE/PROFESOR/BIBLIOTECARIO)
 *   GET    /api/usuarios/buscar?nombre=     → Buscar por nombre
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // POST /api/usuarios — Registrar un usuario
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Registra un nuevo usuario (Estudiante, Profesor o Bibliotecario).
     *
     * @param request Datos del usuario con validaciones
     * @return 201 Created con el usuario registrado
     */
    @PostMapping
    public ResponseEntity<UsuarioResponse> registrarUsuario(@Valid @RequestBody UsuarioRequest request) {
        UsuarioResponse response = usuarioService.registrarUsuario(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // GET /api/usuarios — Listar todos los usuarios
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Retorna la lista completa de usuarios registrados.
     *
     * @return 200 OK con la lista de usuarios
     */
    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listarUsuarios() {
        List<UsuarioResponse> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // GET /api/usuarios/{id} — Consultar un usuario por ID
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Retorna los datos de un usuario específico.
     *
     * @param id ID del usuario
     * @return 200 OK con el usuario, o 404 si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> consultarUsuario(@PathVariable String id) {
        UsuarioResponse response = usuarioService.consultarUsuario(id);
        return ResponseEntity.ok(response);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // PUT /api/usuarios/{id} — Actualizar un usuario
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Actualiza los datos de un usuario existente.
     *
     * @param id      ID del usuario a actualizar
     * @param request Nuevos datos del usuario
     * @return 200 OK con el usuario actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> actualizarUsuario(
            @PathVariable String id,
            @Valid @RequestBody UsuarioRequest request) {
        UsuarioResponse response = usuarioService.actualizarUsuario(id, request);
        return ResponseEntity.ok(response);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // DELETE /api/usuarios/{id} — Eliminar un usuario
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Elimina un usuario del sistema.
     *
     * @param id ID del usuario a eliminar
     * @return 204 No Content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable String id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // GET /api/usuarios/tipo/{tipo} — Listar por tipo
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Lista usuarios filtrados por tipo.
     * Ejemplo: GET /api/usuarios/tipo/ESTUDIANTE
     *
     * @param tipo Tipo de usuario: ESTUDIANTE, PROFESOR o BIBLIOTECARIO
     * @return 200 OK con la lista de usuarios del tipo indicado
     */
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<UsuarioResponse>> listarPorTipo(
            @PathVariable Usuario.TipoUsuario tipo) {
        List<UsuarioResponse> usuarios = usuarioService.listarPorTipo(tipo);
        return ResponseEntity.ok(usuarios);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // GET /api/usuarios/buscar?nombre= — Buscar por nombre
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Busca usuarios cuyo nombre contenga el texto indicado.
     * Ejemplo: GET /api/usuarios/buscar?nombre=juan
     *
     * @param nombre Texto a buscar en el nombre
     * @return 200 OK con la lista de usuarios que coinciden
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<UsuarioResponse>> buscarPorNombre(@RequestParam String nombre) {
        List<UsuarioResponse> usuarios = usuarioService.buscarPorNombre(nombre);
        return ResponseEntity.ok(usuarios);
    }
}
