package cl.duoc.bibliosfera.bibliosfera_api.controller;

import cl.duoc.bibliosfera.bibliosfera_api.dto.ObraDTO;
import cl.duoc.bibliosfera.bibliosfera_api.model.Obra;
import cl.duoc.bibliosfera.bibliosfera_api.service.ObraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/obras")
public class ObraController {

    @Autowired
    private ObraService obraService;

    @GetMapping
    public List<Obra> getAllObras() {
        return obraService.findAll();
    }

    @PostMapping
    public ResponseEntity<Obra> createObra(@RequestBody ObraDTO obraDTO) {
        Obra obraGuardada = obraService.create(obraDTO);
        return new ResponseEntity<>(obraGuardada, HttpStatus.CREATED);
    }
}