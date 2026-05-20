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
 * Sigue el mismo patrón que LibroServiceImpl.
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UsuarioResponse registrarUsuario(UsuarioRequest request) {
        // Regla de negocio: el correo debe ser único
        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
            throw new RuntimeException("Ya existe un usuario con el correo: " + request.getCorreo());
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setCorreo(request.getCorreo());
        usuario.setTipoUsuario(request.getTipoUsuario());
        usuario.setCodigoEstudiante(request.getCodigoEstudiante());
        usuario.setPrograma(request.getPrograma());
        usuario.setCodigoProfesor(request.getCodigoProfesor());
        usuario.setFacultad(request.getFacultad());
        usuario.setCodigoEmpleado(request.getCodigoEmpleado());
        usuario.setTurno(request.getTurno());

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return mapToResponse(usuarioGuardado);
    }

    @Override
    public UsuarioResponse actualizarUsuario(String id, UsuarioRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        usuario.setNombre(request.getNombre());
        usuario.setCorreo(request.getCorreo());
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

    @Override
    public void eliminarUsuario(String id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con id: " + id);
        }
        usuarioRepository.deleteById(id);
    }

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

    // ─────────────────────────────────────────────
    // Método auxiliar: convierte Usuario → UsuarioResponse
    // ─────────────────────────────────────────────
    private UsuarioResponse mapToResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreo(),
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
