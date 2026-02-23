package sv.edu.udb.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sv.edu.udb.model.dto.VentaRequestDTO;
import sv.edu.udb.model.dto.VentaResponseDTO;
import sv.edu.udb.service.VentaService;

import java.util.List;


@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService){
        this.ventaService = ventaService;
    }

    @GetMapping
    public ResponseEntity<List<VentaResponseDTO>> listarVentas(){
        List<VentaResponseDTO> ventasListado = ventaService.listarVentas();

        if(ventasListado.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(ventasListado, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<VentaResponseDTO> registrarVenta(@RequestBody VentaRequestDTO venta){
        VentaResponseDTO ventaRegistrada = ventaService.registrarVenta(venta);

        if(ventaRegistrada == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(ventaRegistrada, HttpStatus.CREATED);
    }
}
