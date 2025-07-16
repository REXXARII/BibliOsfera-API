package cl.duoc.bibliosfera.bibliosfera_api.service;

import cl.duoc.bibliosfera.bibliosfera_api.dto.UsuarioRegistroDTO;
import cl.duoc.bibliosfera.bibliosfera_api.exception.ResourceNotFoundException;
import cl.duoc.bibliosfera.bibliosfera_api.model.Rol;
import cl.duoc.bibliosfera.bibliosfera_api.model.Usuario;
import cl.duoc.bibliosfera.bibliosfera_api.repository.RolRepository;
import cl.duoc.bibliosfera.bibliosfera_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario registrarUsuario(UsuarioRegistroDTO registroDTO) {

        Set<Rol> rolesAsignados = new HashSet<>();
        if (registroDTO.getRoles() == null || registroDTO.getRoles().isEmpty()) {
            // Si no se especifican roles, se le asigna ROLE_CLIENTE por defecto
            Rol rolCliente = rolRepository.findByNombre("ROLE_CLIENTE")
                    .orElseThrow(() -> new ResourceNotFoundException("No se encontró el rol por defecto 'ROLE_CLIENTE'"));
            rolesAsignados.add(rolCliente);
        } else {
            // Busca cada rol por su nombre y lo añade al conjunto
            rolesAsignados = registroDTO.getRoles().stream()
                    .map(nombreRol -> rolRepository.findByNombre(nombreRol)
                            .orElseThrow(() -> new ResourceNotFoundException("No se encontró el rol: " + nombreRol)))
                    .collect(Collectors.toSet());
        }
        
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(registroDTO.getNombre());
        nuevoUsuario.setEmail(registroDTO.getEmail());
        nuevoUsuario.setPassword(passwordEncoder.encode(registroDTO.getPassword()));
        nuevoUsuario.setFechaRegistro(LocalDate.now());
        nuevoUsuario.setRoles(rolesAsignados);

        return usuarioRepository.save(nuevoUsuario);
    }
}
