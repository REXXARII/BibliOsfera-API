package cl.duoc.bibliosfera.bibliosfera_api.controller;

import cl.duoc.bibliosfera.bibliosfera_api.dto.EjemplarDTO;
import cl.duoc.bibliosfera.bibliosfera_api.model.Ejemplar;
import cl.duoc.bibliosfera.bibliosfera_api.service.EjemplarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ejemplares")
public class EjemplarController {

    @Autowired
    private EjemplarService ejemplarService;

    @PostMapping
    public ResponseEntity<Ejemplar> createEjemplar(@RequestBody EjemplarDTO ejemplarDTO) {
        Ejemplar ejemplarGuardado = ejemplarService.create(ejemplarDTO);
        return new ResponseEntity<>(ejemplarGuardado, HttpStatus.CREATED);
    }
}