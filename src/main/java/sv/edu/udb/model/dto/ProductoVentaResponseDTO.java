package sv.edu.udb.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ProductoVentaResponseDTO {
    private String nombre;
    private int cantidad;
    private double precioVenta;
}
