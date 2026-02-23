package sv.edu.udb.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class ProductoResponseDTO {
    private Long id;
    private String nombre;
    private String marca;
    private String categoria;
    private int cantidad;
    private double precio;
    private boolean activo;
}
