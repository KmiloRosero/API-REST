package com.biblioteca.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones para toda la API.
 * Intercepta las excepciones lanzadas en cualquier Controller y las convierte
 * en respuestas HTTP con formato JSON consistente.
 *
 * Patrón POO: Encapsulamiento — centraliza el manejo de errores en un solo lugar.
 * @RestControllerAdvice aplica este manejador a todos los @RestController del proyecto.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ─────────────────────────────────────────────────────────────────────────
    // 404 Not Found — Recurso no encontrado
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Maneja RuntimeException (recurso no encontrado).
     * Retorna 404 con un mensaje descriptivo.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 400 Bad Request — Argumento inválido (regla de negocio)
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Maneja IllegalArgumentException (datos duplicados, reglas de negocio violadas).
     * Retorna 400 con un mensaje descriptivo.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(
            IllegalArgumentException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 409 Conflict — Estado inválido (ejemplar no disponible, préstamo ya devuelto)
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Maneja IllegalStateException (estado inválido para la operación).
     * Retorna 409 Conflict con un mensaje descriptivo.
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalStateException(
            IllegalStateException ex) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 400 Bad Request — Validación de campos (@Valid)
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Maneja errores de validación de Bean Validation (@Valid, @NotBlank, @Email, etc.).
     * Retorna 400 con un mapa de campo → mensaje de error.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> erroresCampos = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            erroresCampos.put(error.getField(), error.getDefaultMessage());
        }

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Error de validación");
        body.put("errores", erroresCampos);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 500 Internal Server Error — Error inesperado
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Maneja cualquier excepción no controlada.
     * Retorna 500 con un mensaje genérico (no expone detalles internos).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                "Error interno del servidor. Por favor contacte al administrador.");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // MÉTODO AUXILIAR
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Construye el cuerpo de respuesta de error con formato consistente.
     *
     * @param status  Código HTTP de la respuesta
     * @param mensaje Mensaje descriptivo del error
     * @return ResponseEntity con el cuerpo formateado
     */
    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String mensaje) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("mensaje", mensaje);
        return ResponseEntity.status(status).body(body);
    }
}
