package cl.duoc.bibliosfera.bibliosfera_api.service;

import cl.duoc.bibliosfera.bibliosfera_api.dto.PrestamoCreacionDTO;
import cl.duoc.bibliosfera.bibliosfera_api.exception.ResourceNotFoundException;
import cl.duoc.bibliosfera.bibliosfera_api.model.Ejemplar;
import cl.duoc.bibliosfera.bibliosfera_api.model.Prestamo;
import cl.duoc.bibliosfera.bibliosfera_api.model.Usuario;
import cl.duoc.bibliosfera.bibliosfera_api.repository.EjemplarRepository;
import cl.duoc.bibliosfera.bibliosfera_api.repository.PrestamoRepository;
import cl.duoc.bibliosfera.bibliosfera_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EjemplarRepository ejemplarRepository;

    @Transactional // ¡Muy importante! Asegura que toda la operación sea atómica.
    public Prestamo crearPrestamo(PrestamoCreacionDTO prestamoDTO) {
        // 1. Buscar las entidades necesarias
        Usuario usuario = usuarioRepository.findById(prestamoDTO.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + prestamoDTO.getUsuarioId()));

        Ejemplar ejemplar = ejemplarRepository.findById(prestamoDTO.getEjemplarId())
                .orElseThrow(() -> new ResourceNotFoundException("Ejemplar no encontrado con ID: " + prestamoDTO.getEjemplarId()));

        // 2. Validar la lógica de negocio
        if (!"Disponible".equalsIgnoreCase(ejemplar.getEstado())) {
            throw new IllegalStateException("El ejemplar con ID " + ejemplar.getId() + " no está disponible para préstamo.");
        }

        // 3. Actualizar el estado del ejemplar
        ejemplar.setEstado("Prestado");
        ejemplarRepository.save(ejemplar);

        // 4. Crear y guardar el nuevo préstamo
        Prestamo nuevoPrestamo = new Prestamo();
        nuevoPrestamo.setUsuario(usuario);
        nuevoPrestamo.setEjemplar(ejemplar);
        nuevoPrestamo.setFechaPrestamo(LocalDate.now());
        nuevoPrestamo.setFechaDevolucionPrevista(LocalDate.now().plusDays(prestamoDTO.getDiasDePrestamo()));
        nuevoPrestamo.setEstado("ACTIVO");

        return prestamoRepository.save(nuevoPrestamo);
    }
}
