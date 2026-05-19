package com.biblioteca.service;

import com.biblioteca.dto.EjemplarRequest;
import com.biblioteca.dto.EjemplarResponse;
import com.biblioteca.model.Ejemplar;

import java.util.List;

/**
 * Interfaz que define el contrato de operaciones para la gestión de Ejemplares.
 */
public interface EjemplarService {

    /**
     * Registra un nuevo ejemplar en el sistema.
     *
     * @param request Datos del ejemplar a registrar
     * @return El ejemplar registrado con su ID generado
     * @throws RuntimeException         si el libro referenciado no existe
     * @throws IllegalArgumentException si ya existe un ejemplar con el mismo código
     */
    EjemplarResponse registrarEjemplar(EjemplarRequest request);

    /**
     * Actualiza los datos de un ejemplar existente.
     *
     * @param id      ID del ejemplar a actualizar
     * @param request Nuevos datos del ejemplar
     * @return El ejemplar actualizado
     * @throws RuntimeException si el ejemplar no existe
     */
    EjemplarResponse actualizarEjemplar(String id, EjemplarRequest request);

    /**
     * Elimina un ejemplar del sistema.
     *
     * @param id ID del ejemplar a eliminar
     * @throws RuntimeException si el ejemplar no existe o tiene préstamos activos
     */
    void eliminarEjemplar(String id);

    /**
     * Consulta un ejemplar por su ID.
     *
     * @param id ID del ejemplar
     * @return El ejemplar encontrado
     * @throws RuntimeException si el ejemplar no existe
     */
    EjemplarResponse consultarEjemplar(String id);

    /**
     * Lista todos los ejemplares del sistema.
     *
     * @return Lista de todos los ejemplares
     */
    List<EjemplarResponse> listarEjemplares();

    /**
     * Lista los ejemplares de un libro específico.
     *
     * @param libroId ID del libro
     * @return Lista de ejemplares del libro
     */
    List<EjemplarResponse> listarPorLibro(String libroId);

    /**
     * Lista los ejemplares disponibles de un libro.
     *
     * @param libroId ID del libro
     * @return Lista de ejemplares disponibles
     */
    List<EjemplarResponse> listarDisponiblesPorLibro(String libroId);

    /**
     * Lista ejemplares por estado.
     *
     * @param estado Estado a filtrar
     * @return Lista de ejemplares en ese estado
     */
    List<EjemplarResponse> listarPorEstado(Ejemplar.EstadoEjemplar estado);
}
