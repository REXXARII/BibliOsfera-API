package cl.duoc.bibliosfera.bibliosfera_api.controller;

import cl.duoc.bibliosfera.bibliosfera_api.dto.CategoriaDTO;
import cl.duoc.bibliosfera.bibliosfera_api.model.Categoria;
import cl.duoc.bibliosfera.bibliosfera_api.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public List<Categoria> getAllCategorias() {
        return categoriaService.findAll();
    }

    @PostMapping
    public ResponseEntity<Categoria> createCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        Categoria nuevaCategoria = new Categoria();
        nuevaCategoria.setNombre(categoriaDTO.getNombre());
        nuevaCategoria.setDescripcion(categoriaDTO.getDescripcion());

        Categoria categoriaGuardada = categoriaService.save(nuevaCategoria);
        return new ResponseEntity<>(categoriaGuardada, HttpStatus.CREATED);
    }
}
