package cl.duoc.bibliosfera.bibliosfera_api.controller;

import cl.duoc.bibliosfera.bibliosfera_api.dto.ProveedorDTO;
import cl.duoc.bibliosfera.bibliosfera_api.model.Proveedor;
import cl.duoc.bibliosfera.bibliosfera_api.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @PostMapping
    @PreAuthorize("hasAnyRole('EMPLEADO', 'ADMIN')")
    public ResponseEntity<Proveedor> createProveedor(@RequestBody ProveedorDTO proveedorDTO) {
        Proveedor nuevoProveedor = new Proveedor();
        nuevoProveedor.setNombre(proveedorDTO.getNombre());
        nuevoProveedor.setContacto(proveedorDTO.getContacto());
        nuevoProveedor.setTelefono(proveedorDTO.getTelefono());
        nuevoProveedor.setEmail(proveedorDTO.getEmail());

        Proveedor proveedorGuardado = proveedorService.save(nuevoProveedor);
        return new ResponseEntity<>(proveedorGuardado, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('EMPLEADO', 'ADMIN')")
    public List<Proveedor> getAllProveedores() {
        return proveedorService.findAll();
    }
}
