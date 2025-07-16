package cl.duoc.bibliosfera.bibliosfera_api.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ejemplares")
@Data
public class Ejemplar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ejemplar")
    private Long id;

    @Column(name = "codigo_barras", unique = true, nullable = false)
    private String codigoBarras;

    @Column(nullable = false)
    private String estado; // Ej: "Disponible", "Prestado", "En Reparación"

    @Column(name = "ubicacion_fisica")
    private String ubicacionFisica;

    // --- Relación Muchos a Uno con Edicion ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edicion_id", nullable = false)
    private Edicion edicion;
}