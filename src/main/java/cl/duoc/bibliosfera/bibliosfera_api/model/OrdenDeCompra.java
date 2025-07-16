package cl.duoc.bibliosfera.bibliosfera_api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ordenes_de_compra")
@Data
public class OrdenDeCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id", nullable = false)
    private Proveedor proveedor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empleado_id", nullable = false)
    private Usuario empleado;

    @Column(name = "fecha_orden", nullable = false)
    private LocalDate fechaOrden;

    @Column(name = "monto_total", nullable = false)
    private BigDecimal montoTotal;

    @Column(nullable = false)
    private String estado; // Ej: "PENDIENTE", "RECIBIDA", "CANCELADA"

    @OneToMany(mappedBy = "ordenDeCompra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleOrdenDeCompra> detalles = new ArrayList<>();
}
