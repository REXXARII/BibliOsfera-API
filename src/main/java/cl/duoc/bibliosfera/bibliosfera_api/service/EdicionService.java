package cl.duoc.bibliosfera.bibliosfera_api.service;

import cl.duoc.bibliosfera.bibliosfera_api.dto.EdicionDTO;
import cl.duoc.bibliosfera.bibliosfera_api.exception.ResourceNotFoundException;
import cl.duoc.bibliosfera.bibliosfera_api.model.Edicion;
import cl.duoc.bibliosfera.bibliosfera_api.model.Obra;
import cl.duoc.bibliosfera.bibliosfera_api.model.Editorial;
import cl.duoc.bibliosfera.bibliosfera_api.repository.EdicionRepository;
import cl.duoc.bibliosfera.bibliosfera_api.repository.EditorialRepository;
import cl.duoc.bibliosfera.bibliosfera_api.repository.ObraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EdicionService {

    @Autowired
    private EdicionRepository edicionRepository;

    @Autowired
    private ObraRepository obraRepository;
    
    @Autowired
    private EditorialRepository editorialRepository;

    public List<Edicion> findAll() {
        return edicionRepository.findAll();
    }

    public Edicion create(EdicionDTO edicionDTO) {
        Obra obra = obraRepository.findById(edicionDTO.getObraId())
            .orElseThrow(() -> new ResourceNotFoundException("No se encontró la Obra con ID: " + edicionDTO.getObraId()));

        Editorial editorial = editorialRepository.findById(edicionDTO.getEditorialId())
            .orElseThrow(() -> new ResourceNotFoundException("No se encontró la Editorial con ID: " + edicionDTO.getEditorialId()));

        Edicion nuevaEdicion = new Edicion();
        nuevaEdicion.setIsbn(edicionDTO.getIsbn());
        nuevaEdicion.setFechaPublicacion(edicionDTO.getFechaPublicacion());
        nuevaEdicion.setObra(obra); 
        nuevaEdicion.setEditorial(editorial);

        return edicionRepository.save(nuevaEdicion);
    }
}
