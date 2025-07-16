package cl.duoc.bibliosfera.bibliosfera_api.dto;

import lombok.Data;

@Data
public class EjemplarDTO {
    private String codigoBarras;
    private String estado;
    private String ubicacionFisica;
    private Long edicionId; // ID de la Edición a la que pertenece este ejemplar
}