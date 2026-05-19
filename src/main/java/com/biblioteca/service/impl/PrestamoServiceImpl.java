package com.biblioteca.service.impl;

import com.biblioteca.dto.PrestamoRequest;
import com.biblioteca.dto.PrestamoResponse;
import com.biblioteca.model.Ejemplar;
import com.biblioteca.model.Prestamo;
import com.biblioteca.model.Usuario;
import com.biblioteca.repository.EjemplarRepository;
import com.biblioteca.repository.LibroRepository;
import com.biblioteca.repository.PrestamoRepository;
import com.biblioteca.repository.UsuarioRepository;
import com.biblioteca.service.PrestamoService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación concreta de PrestamoService.
 * Es el servicio más complejo: aplica las reglas de negocio del tutorial:
 *
 *   REGLA 1: Al crear un préstamo, verificar que el ejemplar esté DISPONIBLE.
 *   REGLA 2: Al crear un préstamo, cambiar el estado del ejemplar a PRESTADO.
 *   REGLA 3: Al registrar una devolución, cambiar el ejemplar a DISPONIBLE.
 *   REGLA 4: Al registrar una devolución, cambiar el préstamo a DEVUELTO.
 */
@Service
public class PrestamoServiceImpl implements PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EjemplarRepository ejemplarRepository;
    private final LibroRepository libroRepository;

    public PrestamoServiceImpl(PrestamoRepository prestamoRepository,
                                UsuarioRepository usuarioRepository,
                                EjemplarRepository ejemplarRepository,
                                LibroRepository libroRepository) {
        this.prestamoRepository = prestamoRepository;
        this.usuarioRepository = usuarioRepository;
        this.ejemplarRepository = ejemplarRepository;
        this.libroRepository = libroRepository;
    }

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

        // 3. REGLA: el ejemplar debe estar en estado DISPONIBLE
        if (ejemplar.getEstado() != Ejemplar.EstadoEjemplar.DISPONIBLE) {
            throw new RuntimeException(
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

        // 5. REGLA: cambiar el estado del ejemplar a PRESTADO
        ejemplar.setEstado(Ejemplar.EstadoEjemplar.PRESTADO);
        ejemplarRepository.save(ejemplar);

        // 6. Guardar el préstamo en MongoDB
        Prestamo prestamoGuardado = prestamoRepository.save(prestamo);

        return mapToResponse(prestamoGuardado, usuario, ejemplar);
    }

    @Override
    public PrestamoResponse registrarDevolucion(String prestamoId) {
        // 1. Buscar el préstamo
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new RuntimeException(
                        "Préstamo no encontrado con id: " + prestamoId));

        // 2. Verificar que el préstamo esté ACTIVO
        if (prestamo.getEstado() != Prestamo.EstadoPrestamo.ACTIVO) {
            throw new RuntimeException(
                    "El préstamo no está activo. Estado actual: " + prestamo.getEstado());
        }

        // 3. Buscar el ejemplar asociado
        Ejemplar ejemplar = ejemplarRepository.findById(prestamo.getEjemplarId())
                .orElseThrow(() -> new RuntimeException(
                        "Ejemplar no encontrado con id: " + prestamo.getEjemplarId()));

        // 4. Registrar la devolución: fecha real = hoy, estado = DEVUELTO
        prestamo.setFechaDevolucionReal(LocalDate.now());
        prestamo.setEstado(Prestamo.EstadoPrestamo.DEVUELTO);

        // 5. REGLA: cambiar el estado del ejemplar a DISPONIBLE
        ejemplar.setEstado(Ejemplar.EstadoEjemplar.DISPONIBLE);
        ejemplarRepository.save(ejemplar);

        // 6. Guardar el préstamo actualizado
        Prestamo prestamoActualizado = prestamoRepository.save(prestamo);

        Usuario usuario = usuarioRepository.findById(prestamo.getUsuarioId()).orElse(null);
        return mapToResponse(prestamoActualizado, usuario, ejemplar);
    }

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
        // Préstamos cuya fecha esperada ya pasó y siguen ACTIVOS
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

    // ─────────────────────────────────────────────
    // Método auxiliar: convierte Prestamo → PrestamoResponse
    // ─────────────────────────────────────────────
    private PrestamoResponse mapToResponse(Prestamo prestamo, Usuario usuario, Ejemplar ejemplar) {
        String tituloLibro = "";
        if (ejemplar != null) {
            tituloLibro = libroRepository.findById(ejemplar.getLibroId())
                    .map(l -> l.getTitulo())
                    .orElse("Libro no encontrado");
        }

        return new PrestamoResponse(
                prestamo.getId(),
                prestamo.getUsuarioId(),
                usuario != null ? usuario.getNombre() : "Usuario no encontrado",
                prestamo.getEjemplarId(),
                ejemplar != null ? ejemplar.getCodigoEjemplar() : "Ejemplar no encontrado",
                tituloLibro,
                prestamo.getFechaPrestamo(),
                prestamo.getFechaDevolucionEsperada(),
                prestamo.getFechaDevolucionReal(),
                prestamo.getEstado()
        );
    }
}
