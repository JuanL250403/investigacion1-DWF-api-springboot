package sv.edu.udb.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoRequestDTO {
    private String nombre;
    private Long marcaId;
    private Long categoriaId;
    private double precio;
    private int cantidad;
}
