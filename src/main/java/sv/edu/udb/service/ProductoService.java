package sv.edu.udb.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import sv.edu.udb.model.dto.ProductoRequestDTO;
import sv.edu.udb.model.dto.ProductoResponseDTO;
import sv.edu.udb.model.entity.Categoria;
import sv.edu.udb.model.entity.Marca;
import sv.edu.udb.model.entity.Producto;
import sv.edu.udb.repository.CategoriaRepository;
import sv.edu.udb.repository.MarcaRepository;
import sv.edu.udb.repository.ProductoRepository;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final MarcaRepository marcaRepository;

    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository, MarcaRepository marcaRepository){
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.marcaRepository = marcaRepository;
    }

    public List<ProductoResponseDTO> listarProductos(){
        return productoRepository.findAll().stream()
                .map(p -> new ProductoResponseDTO(p.getNombre(), p.getMarca().getNombre(), p.getCategoria().getNombre(), p.getCantidad(), p.getPrecio()))
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductoResponseDTO agregarProducto(ProductoRequestDTO producto){

        Categoria categoria = categoriaRepository.findById(producto.getCategoriaId()).orElseThrow();

        Marca marca = marcaRepository.findById(producto.getMarcaId()).orElseThrow();

        Producto productoNuevo = Producto.builder()
                .nombre(producto.getNombre())
                .marca(marca)
                .categoria(categoria)
                .precio(producto.getPrecio())
                .cantidad(producto.getCantidad())
                .build();

        Producto productoCreado = productoRepository.save(productoNuevo);

        return new ProductoResponseDTO(
                productoCreado.getNombre(),
                productoCreado.getMarca().getNombre(),
                productoCreado.getCategoria().getNombre(),
                productoCreado.getCantidad(),
                productoCreado.getPrecio());
    }

    @Transactional
    public ProductoResponseDTO actualizarProducto(Long id, ProductoRequestDTO producto){
        Producto productoActualizar = productoRepository.findById(id).orElse(null);

        if(productoActualizar == null){
            return null;
        }

        Categoria categoria = categoriaRepository.findById(producto.getCategoriaId()).orElseThrow();

        Marca marca = marcaRepository.findById(producto.getMarcaId()).orElseThrow();

        productoActualizar.setNombre(producto.getNombre());
        productoActualizar.setMarca(marca);
        productoActualizar.setCategoria(categoria);
        productoActualizar.setCantidad(producto.getCantidad());
        productoActualizar.setPrecio(producto.getPrecio());

        Producto productoEditado =  productoRepository.save(productoActualizar);

        return new ProductoResponseDTO(
                productoEditado.getNombre(),
                productoEditado.getMarca().getNombre(),
                productoEditado.getCategoria().getNombre(),
                productoEditado.getCantidad(),
                productoEditado.getPrecio());
    }

    @Transactional
    public boolean eliminarProducto(Long id){
        Producto productoElimiar = productoRepository.findById(id).orElse(null);

        if(productoElimiar == null){
            return false;
        }

        productoRepository.delete(productoElimiar);

        return true;
    }
}
