package cl.duoc.bibliosfera.bibliosfera_api.service;

import cl.duoc.bibliosfera.bibliosfera_api.exception.ResourceNotFoundException;
import cl.duoc.bibliosfera.bibliosfera_api.model.Autor;
import cl.duoc.bibliosfera.bibliosfera_api.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    public Autor findById(Long id) {
        return autorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Autor no encontrado con ID: " + id));
    }

    public List<Autor> findAll() {
        return autorRepository.findAll();
    }

    public Autor save(Autor autor) {
        return autorRepository.save(autor);
    }
}