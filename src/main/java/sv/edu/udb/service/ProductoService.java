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
                .map(p -> ProductoResponseDTO.builder()
                        .id(p.getId())
                        .nombre(p.getNombre())
                        .marca(p.getMarca().getNombre())
                        .categoria(p.getCategoria().getNombre())
                        .cantidad(p.getCantidad())
                        .precio(p.getPrecio())
                        .activo(p.getActivo())
                        .build() )
                .collect(Collectors.toList());
    }

    public ProductoResponseDTO buscarProductoId(Long id){
        Producto productoEncontrado = productoRepository.findById(id).orElse(null);

        if(productoEncontrado == null){
            return null;
        }

        return ProductoResponseDTO.builder()
                .id(productoEncontrado.getId())
                .nombre(productoEncontrado.getNombre())
                .marca(productoEncontrado.getMarca().getNombre())
                .categoria(productoEncontrado.getCategoria().getNombre())
                .precio(productoEncontrado.getPrecio())
                .cantidad(productoEncontrado.getCantidad())
                .activo(productoEncontrado.getActivo())
                .build();
    }

    @Transactional
    public ProductoResponseDTO agregarProducto(ProductoRequestDTO producto){

        Categoria categoria = categoriaRepository.findById(producto.getCategoriaId()).orElse(null);

        Marca marca = marcaRepository.findById(producto.getMarcaId()).orElse(null);

        if(marca == null || categoria == null){
            return  null;
        }

        Producto productoNuevo = Producto.builder()
                .nombre(producto.getNombre())
                .marca(marca)
                .categoria(categoria)
                .precio(producto.getPrecio())
                .cantidad(producto.getCantidad())
                .activo(true)
                .build();

        Producto productoCreado = productoRepository.save(productoNuevo);

        return ProductoResponseDTO.builder()
                .id(productoNuevo.getId())
                .nombre(productoCreado.getNombre())
                .marca(productoCreado.getMarca().getNombre())
                .categoria(productoCreado.getCategoria().getNombre())
                .cantidad(productoCreado.getCantidad())
                .precio(productoCreado.getPrecio())
                .activo(productoCreado.getActivo())
                .build();
    }

    @Transactional
    public ProductoResponseDTO actualizarProducto(Long id, ProductoRequestDTO producto){
        Producto productoActualizar = productoRepository.findById(id).orElse(null);

        if(productoActualizar == null){
            return null;
        }

        Categoria categoria = categoriaRepository.findById(producto.getCategoriaId()).orElse(null);

        Marca marca = marcaRepository.findById(producto.getMarcaId()).orElse(null);

        if(marca == null || categoria == null){
            return  null;
        }

        productoActualizar.setNombre(producto.getNombre());
        productoActualizar.setMarca(marca);
        productoActualizar.setCategoria(categoria);
        productoActualizar.setCantidad(producto.getCantidad());
        productoActualizar.setPrecio(producto.getPrecio());

        Producto productoEditado =  productoRepository.save(productoActualizar);

        return  ProductoResponseDTO.builder()
                .id(productoEditado.getId())
                .nombre(productoEditado.getNombre())
                .marca(productoEditado.getMarca().getNombre())
                .categoria(productoEditado.getCategoria().getNombre())
                .cantidad(productoEditado.getCantidad())
                .precio(productoEditado.getPrecio())
                .activo(productoEditado.getActivo())
                .build();
    }

    @Transactional
    public boolean eliminarProducto(Long id){
        Producto productoElimiar = productoRepository.findById(id).orElse(null);

        if(productoElimiar == null){
            return false;
        }
        productoElimiar.setActivo(false);

        productoRepository.save(productoElimiar);

        return true;
    }
}
