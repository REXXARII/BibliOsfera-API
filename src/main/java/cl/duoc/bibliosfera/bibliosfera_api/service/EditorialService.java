package cl.duoc.bibliosfera.bibliosfera_api.service;

import cl.duoc.bibliosfera.bibliosfera_api.model.Editorial;
import cl.duoc.bibliosfera.bibliosfera_api.repository.EditorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EditorialService {

    @Autowired
    private EditorialRepository editorialRepository;

    public List<Editorial> findAll() {
        return editorialRepository.findAll();
    }

    public Editorial save(Editorial editorial) {
        return editorialRepository.save(editorial);
    }
}