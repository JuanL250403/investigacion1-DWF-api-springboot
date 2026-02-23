package sv.edu.udb.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sv.edu.udb.model.dto.ProductoRequestDTO;
import sv.edu.udb.model.dto.ProductoResponseDTO;
import sv.edu.udb.model.entity.Producto;
import sv.edu.udb.repository.CategoriaRepository;
import sv.edu.udb.repository.MarcaRepository;
import sv.edu.udb.service.ProductoService;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService){
        this.productoService = productoService;
    }

    @GetMapping
    private ResponseEntity<List<ProductoResponseDTO>> listarProductos(){
        List<ProductoResponseDTO> listaProductos = productoService.listarProductos();

        if(listaProductos.isEmpty()){
            return new ResponseEntity<>(listaProductos, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(listaProductos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private ResponseEntity<ProductoResponseDTO> buscarProductoId(@PathVariable("id") Long id){
        ProductoResponseDTO producto = productoService.buscarProductoId(id);

        if(producto == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<ProductoResponseDTO> agregarProducto(@RequestBody ProductoRequestDTO producto){
        ProductoResponseDTO productoCreado = productoService.agregarProducto(producto);

        HashMap<String, Object> respuesta = new HashMap<>();

        if(productoCreado == null){
            return ResponseEntity.internalServerError().build();
        }

        return new ResponseEntity<>(productoCreado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    private ResponseEntity<ProductoResponseDTO> actualizarProducto(@PathVariable("id") Long id, @RequestBody ProductoRequestDTO producto){
        ProductoResponseDTO productoActualizado = productoService.actualizarProducto(id, producto);

        if(productoActualizado == null){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(productoActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity eliminarProducto(@PathVariable("id") Long id) {
        boolean eliminado = productoService.eliminarProducto(id);

        if(!eliminado){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
