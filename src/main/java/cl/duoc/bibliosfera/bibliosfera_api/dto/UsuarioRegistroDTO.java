package cl.duoc.bibliosfera.bibliosfera_api.dto;

import lombok.Data;
import java.util.Set;

@Data
public class UsuarioRegistroDTO {
    private String nombre;
    private String email;
    private String password;
    private Set<String> roles; // Recibimos los nombres de los roles, ej: ["ROLE_CLIENTE"]
}
