package com.biblioteca.service;

import com.biblioteca.dto.EjemplarRequest;
import com.biblioteca.dto.EjemplarResponse;
import com.biblioteca.model.Ejemplar;

import java.util.List;

/**
 * Interfaz que define el contrato de operaciones para la gestión de Ejemplares.
 * El Controller solo conoce esta interfaz, no la implementación concreta.
 */
public interface EjemplarService {

    EjemplarResponse registrarEjemplar(EjemplarRequest request);

    EjemplarResponse actualizarEjemplar(String id, EjemplarRequest request);

    void eliminarEjemplar(String id);

    EjemplarResponse consultarEjemplar(String id);

    List<EjemplarResponse> listarEjemplares();

    List<EjemplarResponse> listarPorLibro(String libroId);

    List<EjemplarResponse> listarDisponiblesPorLibro(String libroId);

    List<EjemplarResponse> listarPorEstado(Ejemplar.EstadoEjemplar estado);
}
