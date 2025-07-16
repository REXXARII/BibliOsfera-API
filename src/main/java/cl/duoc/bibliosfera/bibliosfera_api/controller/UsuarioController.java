package cl.duoc.bibliosfera.bibliosfera_api.controller;

import cl.duoc.bibliosfera.bibliosfera_api.dto.UsuarioRegistroDTO;
import cl.duoc.bibliosfera.bibliosfera_api.model.Usuario;
import cl.duoc.bibliosfera.bibliosfera_api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/register")
    public ResponseEntity<?> registrarUsuario(@RequestBody UsuarioRegistroDTO registroDTO) {
        try {
            Usuario usuarioRegistrado = usuarioService.registrarUsuario(registroDTO);
            // No devolvemos la contraseña en la respuesta
            usuarioRegistrado.setPassword(null); 
            return new ResponseEntity<>(usuarioRegistrado, HttpStatus.CREATED);
        } catch (Exception e) {
            // Manejo de errores potenciales, como email duplicado
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
