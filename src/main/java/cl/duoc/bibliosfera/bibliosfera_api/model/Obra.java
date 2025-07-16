package cl.duoc.bibliosfera.bibliosfera_api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "obras")
@Data
public class Obra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_obra")
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(name = "anio_publicacion_original")
    private Integer anioPublicacionOriginal;

    // --- Relación Muchos a Muchos con Autor ---
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
        name = "obra_autor", // Nombre de la tabla intermedia
        joinColumns = @JoinColumn(name = "obra_id"),
        inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private Set<Autor> autores = new HashSet<>();
}