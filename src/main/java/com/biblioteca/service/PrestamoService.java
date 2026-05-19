package com.biblioteca.service;

import com.biblioteca.dto.PrestamoRequest;
import com.biblioteca.dto.PrestamoResponse;

import java.util.List;

/**
 * Interfaz que define el contrato de operaciones para la gestión de Préstamos.
 * Es el servicio más complejo: coordina la lógica entre Usuarios, Ejemplares y Préstamos.
 */
public interface PrestamoService {

    /**
     * Registra un nuevo préstamo en el sistema.
     * Reglas de negocio:
     *   - El usuario debe existir.
     *   - El ejemplar debe existir y estar en estado DISPONIBLE.
     *   - Al crear el préstamo, el ejemplar cambia a estado PRESTADO.
     *   - La fecha de préstamo se asigna automáticamente como la fecha actual.
     *   - El estado inicial del préstamo es ACTIVO.
     *
     * @param request Datos del préstamo a registrar
     * @return El préstamo registrado con su ID generado
     * @throws RuntimeException si el usuario o ejemplar no existen
     * @throws IllegalStateException si el ejemplar no está disponible
     */
    PrestamoResponse registrarPrestamo(PrestamoRequest request);

    /**
     * Registra la devolución de un ejemplar prestado.
     * Reglas de negocio:
     *   - El préstamo debe existir y estar en estado ACTIVO.
     *   - Al devolver, el ejemplar cambia a estado DISPONIBLE.
     *   - La fecha de devolución real se asigna automáticamente como la fecha actual.
     *   - El estado del préstamo cambia a DEVUELTO.
     *
     * @param prestamoId ID del préstamo a devolver
     * @return El préstamo actualizado con la fecha de devolución real
     * @throws RuntimeException      si el préstamo no existe
     * @throws IllegalStateException si el préstamo no está en estado ACTIVO
     */
    PrestamoResponse registrarDevolucion(String prestamoId);

    /**
     * Consulta un préstamo por su ID.
     *
     * @param id ID del préstamo
     * @return El préstamo encontrado
     * @throws RuntimeException si el préstamo no existe
     */
    PrestamoResponse consultarPrestamo(String id);

    /**
     * Lista todos los préstamos del sistema.
     *
     * @return Lista de todos los préstamos
     */
    List<PrestamoResponse> listarPrestamos();

    /**
     * Lista los préstamos de un usuario específico.
     *
     * @param usuarioId ID del usuario
     * @return Lista de préstamos del usuario
     */
    List<PrestamoResponse> listarPorUsuario(String usuarioId);

    /**
     * Lista los préstamos activos (ejemplares aún no devueltos).
     *
     * @return Lista de préstamos activos
     */
    List<PrestamoResponse> listarPrestamosActivos();

    /**
     * Lista los préstamos vencidos (fecha de devolución esperada ya pasó y siguen activos).
     *
     * @return Lista de préstamos vencidos
     */
    List<PrestamoResponse> listarPrestamosVencidos();
}
