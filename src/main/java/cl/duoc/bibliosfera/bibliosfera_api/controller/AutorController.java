package cl.duoc.bibliosfera.bibliosfera_api.controller;

import cl.duoc.bibliosfera.bibliosfera_api.dto.AutorDTO;
import cl.duoc.bibliosfera.bibliosfera_api.model.Autor;
import cl.duoc.bibliosfera.bibliosfera_api.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/autores")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @GetMapping
    public List<Autor> getAllAutores() {
        return autorService.findAll();
    }

    @PostMapping
    public ResponseEntity<Autor> createAutor(@RequestBody AutorDTO autorDTO) {
        Autor autor = new Autor();
        autor.setNombre(autorDTO.getNombre());
        autor.setBiografia(autorDTO.getBiografia());
        Autor autorGuardado = autorService.save(autor);
        return new ResponseEntity<>(autorGuardado, HttpStatus.CREATED);
    }
}