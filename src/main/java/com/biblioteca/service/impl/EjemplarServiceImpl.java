package com.biblioteca.service.impl;

import com.biblioteca.dto.EjemplarRequest;
import com.biblioteca.dto.EjemplarResponse;
import com.biblioteca.model.Ejemplar;
import com.biblioteca.model.Libro;
import com.biblioteca.repository.EjemplarRepository;
import com.biblioteca.repository.LibroRepository;
import com.biblioteca.service.EjemplarService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación concreta de EjemplarService.
 * Sigue el mismo patrón que LibroServiceImpl.
 */
@Service
public class EjemplarServiceImpl implements EjemplarService {

    private final EjemplarRepository ejemplarRepository;
    private final LibroRepository libroRepository;

    public EjemplarServiceImpl(EjemplarRepository ejemplarRepository,
                                LibroRepository libroRepository) {
        this.ejemplarRepository = ejemplarRepository;
        this.libroRepository = libroRepository;
    }

    @Override
    public EjemplarResponse registrarEjemplar(EjemplarRequest request) {
        // Regla de negocio: el libro referenciado debe existir
        Libro libro = libroRepository.findById(request.getLibroId())
                .orElseThrow(() -> new RuntimeException(
                        "No existe un libro con id: " + request.getLibroId()));

        // Regla de negocio: el código del ejemplar debe ser único
        if (ejemplarRepository.existsByCodigoEjemplar(request.getCodigoEjemplar())) {
            throw new RuntimeException(
                    "Ya existe un ejemplar con el código: " + request.getCodigoEjemplar());
        }

        Ejemplar ejemplar = new Ejemplar();
        ejemplar.setCodigoEjemplar(request.getCodigoEjemplar());
        ejemplar.setLibroId(request.getLibroId());
        ejemplar.setEstado(request.getEstado());
        ejemplar.setUbicacion(request.getUbicacion());

        Ejemplar ejemplarGuardado = ejemplarRepository.save(ejemplar);
        return mapToResponse(ejemplarGuardado, libro.getTitulo());
    }

    @Override
    public EjemplarResponse actualizarEjemplar(String id, EjemplarRequest request) {
        Ejemplar ejemplar = ejemplarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ejemplar no encontrado con id: " + id));

        Libro libro = libroRepository.findById(request.getLibroId())
                .orElseThrow(() -> new RuntimeException(
                        "No existe un libro con id: " + request.getLibroId()));

        ejemplar.setCodigoEjemplar(request.getCodigoEjemplar());
        ejemplar.setLibroId(request.getLibroId());
        ejemplar.setEstado(request.getEstado());
        ejemplar.setUbicacion(request.getUbicacion());

        Ejemplar ejemplarActualizado = ejemplarRepository.save(ejemplar);
        return mapToResponse(ejemplarActualizado, libro.getTitulo());
    }

    @Override
    public void eliminarEjemplar(String id) {
        if (!ejemplarRepository.existsById(id)) {
            throw new RuntimeException("Ejemplar no encontrado con id: " + id);
        }
        ejemplarRepository.deleteById(id);
    }

    @Override
    public EjemplarResponse consultarEjemplar(String id) {
        Ejemplar ejemplar = ejemplarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ejemplar no encontrado con id: " + id));

        String tituloLibro = libroRepository.findById(ejemplar.getLibroId())
                .map(Libro::getTitulo)
                .orElse("Libro no encontrado");

        return mapToResponse(ejemplar, tituloLibro);
    }

    @Override
    public List<EjemplarResponse> listarEjemplares() {
        return ejemplarRepository.findAll()
                .stream()
                .map(e -> {
                    String titulo = libroRepository.findById(e.getLibroId())
                            .map(Libro::getTitulo)
                            .orElse("Libro no encontrado");
                    return mapToResponse(e, titulo);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<EjemplarResponse> listarPorLibro(String libroId) {
        String tituloLibro = libroRepository.findById(libroId)
                .map(Libro::getTitulo)
                .orElse("Libro no encontrado");

        return ejemplarRepository.findByLibroId(libroId)
                .stream()
                .map(e -> mapToResponse(e, tituloLibro))
                .collect(Collectors.toList());
    }

    @Override
    public List<EjemplarResponse> listarDisponiblesPorLibro(String libroId) {
        String tituloLibro = libroRepository.findById(libroId)
                .map(Libro::getTitulo)
                .orElse("Libro no encontrado");

        return ejemplarRepository.findByLibroIdAndEstado(libroId, Ejemplar.EstadoEjemplar.DISPONIBLE)
                .stream()
                .map(e -> mapToResponse(e, tituloLibro))
                .collect(Collectors.toList());
    }

    @Override
    public List<EjemplarResponse> listarPorEstado(Ejemplar.EstadoEjemplar estado) {
        return ejemplarRepository.findByEstado(estado)
                .stream()
                .map(e -> {
                    String titulo = libroRepository.findById(e.getLibroId())
                            .map(Libro::getTitulo)
                            .orElse("Libro no encontrado");
                    return mapToResponse(e, titulo);
                })
                .collect(Collectors.toList());
    }

    // ─────────────────────────────────────────────
    // Método auxiliar: convierte Ejemplar → EjemplarResponse
    // ─────────────────────────────────────────────
    private EjemplarResponse mapToResponse(Ejemplar ejemplar, String tituloLibro) {
        return new EjemplarResponse(
                ejemplar.getId(),
                ejemplar.getCodigoEjemplar(),
                ejemplar.getLibroId(),
                tituloLibro,
                ejemplar.getEstado(),
                ejemplar.getUbicacion()
        );
    }
}
