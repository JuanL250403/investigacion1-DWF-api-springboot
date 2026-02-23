package sv.edu.udb.model.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class VentaRequestDTO {
    private List<ProductoVentaRequestDTO> productos;
    private double porcentajeIva;
}
