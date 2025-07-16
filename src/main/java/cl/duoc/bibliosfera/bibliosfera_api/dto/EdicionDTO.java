package cl.duoc.bibliosfera.bibliosfera_api.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class EdicionDTO {
    private String isbn;
    private Long editorialId;
    private LocalDate fechaPublicacion;
    private Long obraId; // ID de la Obra a la que pertenece esta edición
}