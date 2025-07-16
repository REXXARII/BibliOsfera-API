package cl.duoc.bibliosfera.bibliosfera_api.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "autores")
@Data
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String biografia;
}