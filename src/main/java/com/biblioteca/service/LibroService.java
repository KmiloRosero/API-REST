package com.biblioteca.service;

import com.biblioteca.dto.LibroRequest;
import com.biblioteca.dto.LibroResponse;

import java.util.List;

/**
 * Interfaz que define el contrato de operaciones para la gestión de Libros.
 *
 * El Controller solo conoce esta interfaz, no la implementación concreta.
 * Esto aplica el Principio de Inversión de Dependencias (SOLID).
 */
public interface LibroService {

    LibroResponse crearLibro(LibroRequest request);

    LibroResponse actualizarLibro(String id, LibroRequest request);

    void eliminarLibro(String id);

    LibroResponse consultarLibro(String id);

    List<LibroResponse> listarLibros();
}
