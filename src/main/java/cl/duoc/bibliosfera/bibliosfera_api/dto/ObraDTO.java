package cl.duoc.bibliosfera.bibliosfera_api.dto;

import lombok.Data;
import java.util.Set;

@Data
public class ObraDTO {
    private String titulo;
    private Integer anioPublicacionOriginal;
    private Set<Long> idsAutores; // Solo necesitamos los IDs de los autores para asociarlos
    private Set<Long> idsCategorias;
}