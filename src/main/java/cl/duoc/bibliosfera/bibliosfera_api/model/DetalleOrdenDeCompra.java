package cl.duoc.bibliosfera.bibliosfera_api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "detalles_orden_de_compra")
@Data
public class DetalleOrdenDeCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orden_de_compra_id", nullable = false)
    private OrdenDeCompra ordenDeCompra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edicion_id", nullable = false)
    private Edicion edicion; // La edición específica del libro que compramos

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "precio_compra_unitario", nullable = false)
    private BigDecimal precioCompraUnitario;
}
