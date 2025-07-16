package cl.duoc.bibliosfera.bibliosfera_api.service;

import cl.duoc.bibliosfera.bibliosfera_api.dto.EjemplarDTO;
import cl.duoc.bibliosfera.bibliosfera_api.exception.ResourceNotFoundException;
import cl.duoc.bibliosfera.bibliosfera_api.model.Edicion;
import cl.duoc.bibliosfera.bibliosfera_api.model.Ejemplar;
import cl.duoc.bibliosfera.bibliosfera_api.repository.EdicionRepository;
import cl.duoc.bibliosfera.bibliosfera_api.repository.EjemplarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EjemplarService {

    @Autowired
    private EjemplarRepository exemplarRepository;

    @Autowired
    private EdicionRepository edicionRepository;

    public Ejemplar create(EjemplarDTO exemplarDTO) {
        // Buscamos la Edición a la que pertenecerá el ejemplar
        Edicion edicion = edicionRepository.findById(exemplarDTO.getEdicionId())
            .orElseThrow(() -> new ResourceNotFoundException("No se encontró la Edición con ID: " + exemplarDTO.getEdicionId()));

        // Creamos la nueva entidad Ejemplar
        Ejemplar nuevoEjemplar = new Ejemplar();
        nuevoEjemplar.setCodigoBarras(exemplarDTO.getCodigoBarras());
        nuevoEjemplar.setEstado(exemplarDTO.getEstado());
        nuevoEjemplar.setUbicacionFisica(exemplarDTO.getUbicacionFisica());
        nuevoEjemplar.setEdicion(edicion); // Asignamos la entidad Edicion completa

        return exemplarRepository.save(nuevoEjemplar);
    }
}