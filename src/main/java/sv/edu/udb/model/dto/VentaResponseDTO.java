package sv.edu.udb.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class VentaResponseDTO {
    private List<ProductoVentaResponseDTO> productos;
    private double subTotal;
    private double porcentajeIva;
    private double iva;
    private double total;
    private String fechaVenta;
}
