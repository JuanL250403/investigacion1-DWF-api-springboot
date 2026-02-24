package sv.edu.udb.service;

import jakarta.transaction.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import sv.edu.udb.model.dto.*;
import sv.edu.udb.model.entity.DetalleVenta;
import sv.edu.udb.model.entity.Producto;
import sv.edu.udb.model.entity.Venta;
import sv.edu.udb.repository.DetalleVentaRepository;
import sv.edu.udb.repository.ProductoRepository;
import sv.edu.udb.repository.VentaRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class VentaService {
    private final DetalleVentaRepository detalleVentaRepository;
    private VentaRepository ventaRepository;
    private ProductoRepository productoRepository;

    public VentaService(ProductoRepository productoRepository, VentaRepository ventaRepository, DetalleVentaRepository detalleVentaRepository) {
        this.ventaRepository = ventaRepository;
        this.productoRepository = productoRepository;
        this.detalleVentaRepository = detalleVentaRepository;
    }

    public List<VentaResponseDTO> listarVentas(){
        List<Venta> ventas = ventaRepository.findAll();

        List<VentaResponseDTO> ventaResponse = new ArrayList<>();

        for(Venta venta : ventas){
            double SubTotal = 0;
            double porcentajeIva = 0;
            double iva = 0;
            double total = 0;

            List<DetalleVenta> detalles = venta.getDetalles();
            List<ProductoVentaResponseDTO> productosVenta = new ArrayList<>();

            for (DetalleVenta detalleVenta : detalles){
                productosVenta.add(
                        ProductoVentaResponseDTO.builder()
                                .nombre(detalleVenta.getProducto().getNombre())
                                .cantidad(detalleVenta.getCantidad())
                                .precioVenta(detalleVenta.getPrecioVenta())
                        .build()
                );

                porcentajeIva = detalleVenta.getPorcentajeIva();
                SubTotal += detalleVenta.getSubTotal();
                iva += detalleVenta.getIva();
                total += detalleVenta.getTotal();
            }

            ventaResponse.add(
                    VentaResponseDTO.builder()
                            .id(venta.getId())
                            .productos(productosVenta)
                            .subTotal(SubTotal)
                            .porcentajeIva(porcentajeIva)
                            .iva(iva)
                            .total(total)
                            .fechaVenta(venta.getFechaVenta().toString())
                    .build()
            );
        }

        return ventaResponse;
    }

    @Transactional
    public VentaResponseDTO registrarVenta(VentaRequestDTO venta){
        List<ProductoVentaRequestDTO> productosVenta = venta.getProductos();

        LocalDateTime fechaVenta = LocalDateTime.now();

        Venta ventaCrear = Venta.builder()
                .fechaVenta(fechaVenta)
                .build();

        Venta ventaCreada = ventaRepository.save(ventaCrear);

        double porcentajeIva = venta.getPorcentajeIva();


        List<DetalleVenta> detallesVentaCrear = new ArrayList<>();
        List<Producto> productosActualizar = new ArrayList<>();

        List<ProductoVentaResponseDTO> productosResponse = new ArrayList<>();

        double subTotalVenta = 0;
        double ivaVenta = 0;
        double totalVenta = 0;

        for(ProductoVentaRequestDTO productoVenta : productosVenta){
            Producto producto = productoRepository.findById(productoVenta.getId()).orElseThrow();

            int cantidadVenta = productoVenta.getCantidad();
            int cantidadProducto = producto.getCantidad();

            if(cantidadProducto < cantidadVenta){
                TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
                return null;
            }

            double precioVenta = producto.getPrecio();
            double subTotal = cantidadVenta * precioVenta;
            double iva = subTotal * (porcentajeIva / 100);
            double total = subTotal + iva;

            subTotalVenta += subTotal;
            ivaVenta += iva;
            totalVenta += total;

            DetalleVenta detalleCrear = DetalleVenta.builder()
                    .venta(ventaCreada)
                    .producto(producto)
                    .precioVenta(precioVenta)
                    .cantidad(cantidadVenta)
                    .iva(iva)
                    .subTotal(subTotal)
                    .porcentajeIva(porcentajeIva)
                    .total(total)
                    .build();

            producto.setCantidad(cantidadProducto - cantidadVenta);

            detallesVentaCrear.add(detalleCrear);
            productosActualizar.add(producto);
            productosResponse.add(
                    ProductoVentaResponseDTO.builder()
                            .nombre(producto.getNombre())
                            .cantidad(cantidadVenta)
                            .precioVenta(precioVenta)
                            .build()
            );
        }

        detalleVentaRepository.saveAll(detallesVentaCrear);
        productoRepository.saveAll(productosActualizar);


        VentaResponseDTO ventaResponse = VentaResponseDTO.builder()
                .id(ventaCreada.getId())
                .productos(productosResponse)
                .subTotal(subTotalVenta)
                .porcentajeIva(porcentajeIva)
                .iva(ivaVenta)
                .total(totalVenta)
                .fechaVenta(fechaVenta.toString())
                .build();

        return ventaResponse;
    }
}
