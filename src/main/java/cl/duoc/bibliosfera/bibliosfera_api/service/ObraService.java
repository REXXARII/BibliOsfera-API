package cl.duoc.bibliosfera.bibliosfera_api.service;

import cl.duoc.bibliosfera.bibliosfera_api.dto.ObraDTO;
import cl.duoc.bibliosfera.bibliosfera_api.exception.ResourceNotFoundException;
import cl.duoc.bibliosfera.bibliosfera_api.model.Autor;
import cl.duoc.bibliosfera.bibliosfera_api.model.Categoria;
import cl.duoc.bibliosfera.bibliosfera_api.model.Obra;
import cl.duoc.bibliosfera.bibliosfera_api.repository.AutorRepository;
import cl.duoc.bibliosfera.bibliosfera_api.repository.CategoriaRepository;
import cl.duoc.bibliosfera.bibliosfera_api.repository.ObraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ObraService {

    @Autowired
    private ObraRepository obraRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Obra> findAll() {
        return obraRepository.findAll();
    }

    public Obra create(ObraDTO obraDTO) {
        Set<Autor> autores = new HashSet<>(autorRepository.findAllById(obraDTO.getIdsAutores()));
        if (autores.isEmpty()) {
            throw new ResourceNotFoundException("No se encontró ningún autor con los IDs proporcionados.");
        }

        Set<Categoria> categorias = new HashSet<>();
        if (obraDTO.getIdsCategorias() != null && !obraDTO.getIdsCategorias().isEmpty()) {
            categorias = new HashSet<>(categoriaRepository.findAllById(obraDTO.getIdsCategorias()));
        }

        Obra nuevaObra = new Obra();
        nuevaObra.setTitulo(obraDTO.getTitulo());
        nuevaObra.setAnioPublicacionOriginal(obraDTO.getAnioPublicacionOriginal());
        nuevaObra.setAutores(autores);
        nuevaObra.setCategorias(categorias);

        return obraRepository.save(nuevaObra);
    }
}
