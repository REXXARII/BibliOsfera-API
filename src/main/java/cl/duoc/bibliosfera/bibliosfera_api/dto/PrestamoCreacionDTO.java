package cl.duoc.bibliosfera.bibliosfera_api.dto;

import lombok.Data;

@Data
public class PrestamoCreacionDTO {
    private Long usuarioId;
    private Long ejemplarId;
    private Integer diasDePrestamo; // Ej: 7, 14, 30
}
