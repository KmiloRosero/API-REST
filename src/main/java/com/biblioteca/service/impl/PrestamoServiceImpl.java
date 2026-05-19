package com.biblioteca.service.impl;

import com.biblioteca.dto.PrestamoRequest;
import com.biblioteca.dto.PrestamoResponse;
import com.biblioteca.model.Ejemplar;
import com.biblioteca.model.Prestamo;
import com.biblioteca.model.Usuario;
import com.biblioteca.repository.EjemplarRepository;
import com.biblioteca.repository.PrestamoRepository;
import com.biblioteca.repository.UsuarioRepository;
import com.biblioteca.service.PrestamoService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación concreta de PrestamoService.
 * Es el servicio más complejo del sistema: coordina la lógica entre
 * Usuarios, Ejemplares y Préstamos, aplicando todas las reglas de negocio.
 *
 * Reglas de negocio implementadas:
 *   1. Solo se puede prestar un ejemplar en estado DISPONIBLE.
 *   2. Al prestar, el ejemplar cambia a estado PRESTADO.
 *   3. Al devolver, el ejemplar vuelve a estado DISPONIBLE.
 *   4. Solo se puede devolver un préstamo en estado ACTIVO.
 *   5. La fecha de préstamo se asigna automáticamente (fecha actual).
 *   6. Un préstamo es VENCIDO si su fecha esperada ya pasó y sigue ACTIVO.
 *
 * Patrón POO: Polimorfismo — implementa la interfaz PrestamoService.
 * Patrón POO: Asociación — coordina Usuario, Ejemplar y Prestamo.
 */
@Service
public class PrestamoServiceImpl implements PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EjemplarRepository ejemplarRepository;

    /**
     * Inyección por constructor de los tres repositorios necesarios.
     */
    public PrestamoServiceImpl(PrestamoRepository prestamoRepository,
                                UsuarioRepository usuarioRepository,
                                EjemplarRepository ejemplarRepository) {
        this.prestamoRepository = prestamoRepository;
        this.usuarioRepository = usuarioRepository;
        this.ejemplarRepository = ejemplarRepository;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // REGISTRAR PRÉSTAMO
    // ─────────────────────────────────────────────────────────────────────────

    @Override
    public PrestamoResponse registrarPrestamo(PrestamoRequest request) {
        // 1. Verificar que el usuario exista
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException(
                        "Usuario no encontrado con id: " + request.getUsuarioId()));

        // 2. Verificar que el ejemplar exista
        Ejemplar ejemplar = ejemplarRepository.findById(request.getEjemplarId())
                .orElseThrow(() -> new RuntimeException(
                        "Ejemplar no encontrado con id: " + request.getEjemplarId()));

        // 3. Regla de negocio: el ejemplar debe estar DISPONIBLE
        if (ejemplar.getEstado() != Ejemplar.EstadoEjemplar.DISPONIBLE) {
            throw new IllegalStateException(
                    "El ejemplar '" + ejemplar.getCodigoEjemplar() +
                    "' no está disponible. Estado actual: " + ejemplar.getEstado());
        }

        // 4. Crear el préstamo con estado ACTIVO y fecha actual
        Prestamo prestamo = new Prestamo();
        prestamo.setUsuarioId(request.getUsuarioId());
        prestamo.setEjemplarId(request.getEjemplarId());
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaDevolucionEsperada(request.getFechaDevolucionEsperada());
        prestamo.setFechaDevolucionReal(null);
        prestamo.setEstado(Prestamo.EstadoPrestamo.ACTIVO);
        prestamo.setObservaciones(request.getObservaciones());

        // 5. Cambiar el estado del ejemplar a PRESTADO
        ejemplar.setEstado(Ejemplar.EstadoEjemplar.PRESTADO);
        ejemplarRepository.save(ejemplar);

        // 6. Guardar el préstamo en MongoDB
        Prestamo prestamoGuardado = prestamoRepository.save(prestamo);

        return mapToResponse(prestamoGuardado, usuario, ejemplar);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // REGISTRAR DEVOLUCIÓN
    // ─────────────────────────────────────────────────────────────────────────

    @Override
    public PrestamoResponse registrarDevolucion(String prestamoId) {
        // 1. Buscar el préstamo
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new RuntimeException(
                        "Préstamo no encontrado con id: " + prestamoId));

        // 2. Regla de negocio: solo se puede devolver un préstamo ACTIVO
        if (prestamo.getEstado() != Prestamo.EstadoPrestamo.ACTIVO) {
            throw new IllegalStateException(
                    "El préstamo no está activo. Estado actual: " + prestamo.getEstado());
        }

        // 3. Buscar el ejemplar asociado
        Ejemplar ejemplar = ejemplarRepository.findById(prestamo.getEjemplarId())
                .orElseThrow(() -> new RuntimeException(
                        "Ejemplar no encontrado con id: " + prestamo.getEjemplarId()));

        // 4. Registrar la devolución: fecha real = hoy, estado = DEVUELTO
        prestamo.setFechaDevolucionReal(LocalDate.now());
        prestamo.setEstado(Prestamo.EstadoPrestamo.DEVUELTO);

        // 5. Cambiar el estado del ejemplar a DISPONIBLE
        ejemplar.setEstado(Ejemplar.EstadoEjemplar.DISPONIBLE);
        ejemplarRepository.save(ejemplar);

        // 6. Guardar el préstamo actualizado
        Prestamo prestamoActualizado = prestamoRepository.save(prestamo);

        // 7. Obtener datos del usuario para la respuesta
        Usuario usuario = usuarioRepository.findById(prestamo.getUsuarioId())
                .orElse(null);

        return mapToResponse(prestamoActualizado, usuario, ejemplar);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // CONSULTAR
    // ─────────────────────────────────────────────────────────────────────────

    @Override
    public PrestamoResponse consultarPrestamo(String id) {
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado con id: " + id));

        Usuario usuario = usuarioRepository.findById(prestamo.getUsuarioId()).orElse(null);
        Ejemplar ejemplar = ejemplarRepository.findById(prestamo.getEjemplarId()).orElse(null);

        return mapToResponse(prestamo, usuario, ejemplar);
    }

    @Override
    public List<PrestamoResponse> listarPrestamos() {
        return prestamoRepository.findAll()
                .stream()
                .map(p -> {
                    Usuario u = usuarioRepository.findById(p.getUsuarioId()).orElse(null);
                    Ejemplar e = ejemplarRepository.findById(p.getEjemplarId()).orElse(null);
                    return mapToResponse(p, u, e);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<PrestamoResponse> listarPorUsuario(String usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);

        return prestamoRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(p -> {
                    Ejemplar e = ejemplarRepository.findById(p.getEjemplarId()).orElse(null);
                    return mapToResponse(p, usuario, e);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<PrestamoResponse> listarPrestamosActivos() {
        return prestamoRepository.findByEstado(Prestamo.EstadoPrestamo.ACTIVO)
                .stream()
                .map(p -> {
                    Usuario u = usuarioRepository.findById(p.getUsuarioId()).orElse(null);
                    Ejemplar e = ejemplarRepository.findById(p.getEjemplarId()).orElse(null);
                    return mapToResponse(p, u, e);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<PrestamoResponse> listarPrestamosVencidos() {
        // Préstamos cuya fecha esperada ya pasó y siguen en estado ACTIVO
        return prestamoRepository
                .findByFechaDevolucionEsperadaBeforeAndEstado(
                        LocalDate.now(), Prestamo.EstadoPrestamo.ACTIVO)
                .stream()
                .map(p -> {
                    Usuario u = usuarioRepository.findById(p.getUsuarioId()).orElse(null);
                    Ejemplar e = ejemplarRepository.findById(p.getEjemplarId()).orElse(null);
                    return mapToResponse(p, u, e);
                })
                .collect(Collectors.toList());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // MÉTODOS AUXILIARES PRIVADOS
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Convierte una entidad Prestamo en PrestamoResponse enriquecido con
     * datos del usuario y del ejemplar para mayor legibilidad.
     *
     * Calcula automáticamente si el préstamo está vencido.
     */
    private PrestamoResponse mapToResponse(Prestamo prestamo, Usuario usuario, Ejemplar ejemplar) {
        boolean vencido = prestamo.getEstado() == Prestamo.EstadoPrestamo.ACTIVO
                && prestamo.getFechaDevolucionEsperada() != null
                && prestamo.getFechaDevolucionEsperada().isBefore(LocalDate.now());

        return new PrestamoResponse(
                prestamo.getId(),
                prestamo.getUsuarioId(),
                usuario != null ? usuario.getNombre() : "Usuario no encontrado",
                prestamo.getEjemplarId(),
                ejemplar != null ? ejemplar.getCodigoEjemplar() : "Ejemplar no encontrado",
                obtenerTituloLibro(ejemplar),
                prestamo.getFechaPrestamo(),
                prestamo.getFechaDevolucionEsperada(),
                prestamo.getFechaDevolucionReal(),
                prestamo.getEstado(),
                prestamo.getObservaciones(),
                vencido
        );
    }

    /**
     * Obtiene el título del libro a partir del ejemplar.
     * Retorna un mensaje descriptivo si no se puede obtener.
     */
    private String obtenerTituloLibro(Ejemplar ejemplar) {
        if (ejemplar == null) return "Libro no encontrado";
        return ejemplar.getLibroId(); // El Controller enriquece esto si se necesita
    }
}
