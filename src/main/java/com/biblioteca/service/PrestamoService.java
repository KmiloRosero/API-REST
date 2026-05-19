package com.biblioteca.service;

import com.biblioteca.dto.PrestamoRequest;
import com.biblioteca.dto.PrestamoResponse;

import java.util.List;

/**
 * Interfaz que define el contrato de operaciones para la gestión de Préstamos.
 *
 * Reglas de negocio clave:
 *   - Al crear un préstamo, el ejemplar debe estar en estado DISPONIBLE.
 *   - Al registrar una devolución, el ejemplar cambia a estado DISPONIBLE.
 */
public interface PrestamoService {

    /**
     * Registra un nuevo préstamo.
     * Verifica que el ejemplar esté DISPONIBLE y lo cambia a PRESTADO.
     */
    PrestamoResponse registrarPrestamo(PrestamoRequest request);

    /**
     * Registra la devolución de un ejemplar.
     * Cambia el estado del ejemplar a DISPONIBLE y el préstamo a DEVUELTO.
     */
    PrestamoResponse registrarDevolucion(String prestamoId);

    PrestamoResponse consultarPrestamo(String id);

    List<PrestamoResponse> listarPrestamos();

    List<PrestamoResponse> listarPorUsuario(String usuarioId);

    List<PrestamoResponse> listarPrestamosActivos();

    List<PrestamoResponse> listarPrestamosVencidos();
}
