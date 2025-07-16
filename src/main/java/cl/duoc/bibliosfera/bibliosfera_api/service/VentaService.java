package cl.duoc.bibliosfera.bibliosfera_api.service;

import cl.duoc.bibliosfera.bibliosfera_api.dto.VentaCreacionDTO;
import cl.duoc.bibliosfera.bibliosfera_api.exception.ResourceNotFoundException;
import cl.duoc.bibliosfera.bibliosfera_api.model.Ejemplar;
import cl.duoc.bibliosfera.bibliosfera_api.model.Usuario;
import cl.duoc.bibliosfera.bibliosfera_api.model.Venta;
import cl.duoc.bibliosfera.bibliosfera_api.repository.EjemplarRepository;
import cl.duoc.bibliosfera.bibliosfera_api.repository.UsuarioRepository;
import cl.duoc.bibliosfera.bibliosfera_api.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private EjemplarRepository ejemplarRepository;

    @Transactional
    public Venta crearVenta(VentaCreacionDTO ventaDTO) {
        // 1. Buscar al cliente y al empleado
        Usuario cliente = usuarioRepository.findById(ventaDTO.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + ventaDTO.getClienteId()));
        Usuario empleado = usuarioRepository.findById(ventaDTO.getEmpleadoId())
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con ID: " + ventaDTO.getEmpleadoId()));

        // 2. Crear la entidad Venta
        Venta nuevaVenta = new Venta();
        nuevaVenta.setCliente(cliente);
        nuevaVenta.setEmpleado(empleado);
        nuevaVenta.setFechaVenta(LocalDate.now());
        nuevaVenta.setMontoTotal(ventaDTO.getMontoTotal());

        // 3. Buscar y asociar los ejemplares
        List<Ejemplar> ejemplaresAVender = ejemplarRepository.findAllById(ventaDTO.getIdsEjemplares());
        if (ejemplaresAVender.size() != ventaDTO.getIdsEjemplares().size()) {
            throw new ResourceNotFoundException("Uno o más ejemplares no fueron encontrados.");
        }

        for (Ejemplar ejemplar : ejemplaresAVender) {
            if (!"Disponible".equalsIgnoreCase(ejemplar.getEstado())) {
                throw new IllegalStateException("El ejemplar con ID " + ejemplar.getId() + " no está disponible para la venta.");
            }
            ejemplar.setEstado("Vendido");
            ejemplar.setVenta(nuevaVenta); // Asociamos el ejemplar a la venta
        }

        nuevaVenta.setEjemplares(ejemplaresAVender);
        
        return ventaRepository.save(nuevaVenta);
    }
}
