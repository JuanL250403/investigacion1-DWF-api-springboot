package sv.edu.udb.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "detalles_ventas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class    DetalleVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(nullable = false)
    private double precioVenta;

    @Column(nullable = false)
    private int cantidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venta_id")
    private Venta venta;

    @Column(nullable = false)
    private double subTotal;

    @Column(nullable = false)
    private double iva;

    @Column(nullable = false)
    private double porcentajeIva;

    private double total;
}
