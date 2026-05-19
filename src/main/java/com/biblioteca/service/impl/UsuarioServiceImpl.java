package com.biblioteca.service.impl;

import com.biblioteca.dto.UsuarioRequest;
import com.biblioteca.dto.UsuarioResponse;
import com.biblioteca.model.Usuario;
import com.biblioteca.repository.UsuarioRepository;
import com.biblioteca.service.UsuarioService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación concreta de UsuarioService.
 * Gestiona la lógica de negocio para todos los tipos de usuario:
 * Estudiante, Profesor y Bibliotecario.
 *
 * Patrón POO: Polimorfismo — implementa la interfaz UsuarioService.
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // REGISTRAR
    // ─────────────────────────────────────────────────────────────────────────

    @Override
    public UsuarioResponse registrarUsuario(UsuarioRequest request) {
        // Regla de negocio: el correo debe ser único en el sistema
        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
            throw new IllegalArgumentException(
                    "Ya existe un usuario con el correo: " + request.getCorreo());
        }

        // Validaciones específicas por tipo de usuario
        validarCamposEspecificos(request);

        Usuario usuario = mapToEntity(request);
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return mapToResponse(usuarioGuardado);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // ACTUALIZAR
    // ─────────────────────────────────────────────────────────────────────────

    @Override
    public UsuarioResponse actualizarUsuario(String id, UsuarioRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        usuario.setNombre(request.getNombre());
        usuario.setCorreo(request.getCorreo());
        usuario.setTelefono(request.getTelefono());
        usuario.setTipoUsuario(request.getTipoUsuario());
        usuario.setCodigoEstudiante(request.getCodigoEstudiante());
        usuario.setPrograma(request.getPrograma());
        usuario.setCodigoProfesor(request.getCodigoProfesor());
        usuario.setFacultad(request.getFacultad());
        usuario.setCodigoEmpleado(request.getCodigoEmpleado());
        usuario.setTurno(request.getTurno());

        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        return mapToResponse(usuarioActualizado);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // ELIMINAR
    // ─────────────────────────────────────────────────────────────────────────

    @Override
    public void eliminarUsuario(String id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con id: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // CONSULTAR
    // ─────────────────────────────────────────────────────────────────────────

    @Override
    public UsuarioResponse consultarUsuario(String id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
        return mapToResponse(usuario);
    }

    @Override
    public List<UsuarioResponse> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioResponse> listarPorTipo(Usuario.TipoUsuario tipoUsuario) {
        return usuarioRepository.findByTipoUsuario(tipoUsuario)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioResponse> buscarPorNombre(String nombre) {
        return usuarioRepository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // MÉTODOS AUXILIARES PRIVADOS
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Valida que los campos específicos del tipo de usuario estén presentes.
     * Aplica POO: encapsulamiento de la lógica de validación.
     */
    private void validarCamposEspecificos(UsuarioRequest request) {
        switch (request.getTipoUsuario()) {
            case ESTUDIANTE -> {
                if (request.getCodigoEstudiante() == null || request.getCodigoEstudiante().isBlank()) {
                    throw new IllegalArgumentException("El código de estudiante es obligatorio para usuarios de tipo ESTUDIANTE");
                }
                if (request.getPrograma() == null || request.getPrograma().isBlank()) {
                    throw new IllegalArgumentException("El programa es obligatorio para usuarios de tipo ESTUDIANTE");
                }
            }
            case PROFESOR -> {
                if (request.getCodigoProfesor() == null || request.getCodigoProfesor().isBlank()) {
                    throw new IllegalArgumentException("El código de profesor es obligatorio para usuarios de tipo PROFESOR");
                }
                if (request.getFacultad() == null || request.getFacultad().isBlank()) {
                    throw new IllegalArgumentException("La facultad es obligatoria para usuarios de tipo PROFESOR");
                }
            }
            case BIBLIOTECARIO -> {
                if (request.getCodigoEmpleado() == null || request.getCodigoEmpleado().isBlank()) {
                    throw new IllegalArgumentException("El código de empleado es obligatorio para usuarios de tipo BIBLIOTECARIO");
                }
                if (request.getTurno() == null || request.getTurno().isBlank()) {
                    throw new IllegalArgumentException("El turno es obligatorio para usuarios de tipo BIBLIOTECARIO");
                }
            }
        }
    }

    /**
     * Convierte un UsuarioRequest en una entidad Usuario.
     */
    private Usuario mapToEntity(UsuarioRequest request) {
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setCorreo(request.getCorreo());
        usuario.setTelefono(request.getTelefono());
        usuario.setTipoUsuario(request.getTipoUsuario());
        usuario.setCodigoEstudiante(request.getCodigoEstudiante());
        usuario.setPrograma(request.getPrograma());
        usuario.setCodigoProfesor(request.getCodigoProfesor());
        usuario.setFacultad(request.getFacultad());
        usuario.setCodigoEmpleado(request.getCodigoEmpleado());
        usuario.setTurno(request.getTurno());
        return usuario;
    }

    /**
     * Convierte una entidad Usuario en un UsuarioResponse.
     */
    private UsuarioResponse mapToResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getTelefono(),
                usuario.getTipoUsuario(),
                usuario.getCodigoEstudiante(),
                usuario.getPrograma(),
                usuario.getCodigoProfesor(),
                usuario.getFacultad(),
                usuario.getCodigoEmpleado(),
                usuario.getTurno()
        );
    }
}
