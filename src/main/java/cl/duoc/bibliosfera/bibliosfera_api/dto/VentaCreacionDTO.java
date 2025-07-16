package cl.duoc.bibliosfera.bibliosfera_api.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class VentaCreacionDTO {
    private Long clienteId;
    private Long empleadoId;
    private List<Long> idsEjemplares;
    private BigDecimal montoTotal; // Por ahora, lo enviaremos desde el cliente.
}
