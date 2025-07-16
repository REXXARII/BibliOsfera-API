package cl.duoc.bibliosfera.bibliosfera_api.controller;

import cl.duoc.bibliosfera.bibliosfera_api.dto.EditorialDTO;
import cl.duoc.bibliosfera.bibliosfera_api.model.Editorial;
import cl.duoc.bibliosfera.bibliosfera_api.service.EditorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/editoriales")
public class EditorialController {

    @Autowired
    private EditorialService editorialService;

    @GetMapping
    public List<Editorial> getAllEditoriales() {
        return editorialService.findAll();
    }

    @PostMapping
    public ResponseEntity<Editorial> createEditorial(@RequestBody EditorialDTO editorialDTO) {
        Editorial nuevaEditorial = new Editorial();
        nuevaEditorial.setNombre(editorialDTO.getNombre());
        nuevaEditorial.setSitioWeb(editorialDTO.getSitioWeb());

        Editorial editorialGuardada = editorialService.save(nuevaEditorial);
        return new ResponseEntity<>(editorialGuardada, HttpStatus.CREATED);
    }
}