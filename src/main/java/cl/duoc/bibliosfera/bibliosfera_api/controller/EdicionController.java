package cl.duoc.bibliosfera.bibliosfera_api.controller;

import cl.duoc.bibliosfera.bibliosfera_api.dto.EdicionDTO;
import cl.duoc.bibliosfera.bibliosfera_api.model.Edicion;
import cl.duoc.bibliosfera.bibliosfera_api.service.EdicionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ediciones")
public class EdicionController {

    @Autowired
    private EdicionService edicionService;

    @GetMapping
    public List<Edicion> getAllEdiciones() {
        return edicionService.findAll();
    }

    @PostMapping
    public ResponseEntity<Edicion> createEdicion(@RequestBody EdicionDTO edicionDTO) {
        Edicion edicionGuardada = edicionService.create(edicionDTO);
        return new ResponseEntity<>(edicionGuardada, HttpStatus.CREATED);
    }
}