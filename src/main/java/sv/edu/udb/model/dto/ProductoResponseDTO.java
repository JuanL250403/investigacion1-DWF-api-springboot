package sv.edu.udb.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductoResponseDTO {
    private String nombre;
    private String marca;
    private String categoria;
    private int cantidad;
    private double precio;
}
