package sv.edu.udb.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Entity
@Table(name = "productos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marca_id", nullable = false)
    private Marca marca;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "producto")
    private List<DetalleVenta> ventas;

    @Column(nullable = false)
    private double precio;

    @Column(nullable = false)
    private int cantidad;

    @ColumnDefault("true")
    @Column(nullable = false)
    private Boolean activo;
}
