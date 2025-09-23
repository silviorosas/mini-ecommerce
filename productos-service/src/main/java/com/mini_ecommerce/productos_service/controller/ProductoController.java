package com.mini_ecommerce.productos_service.controller;

import com.mini_ecommerce.productos_service.dto.ProductoDto;
import com.mini_ecommerce.productos_service.service.ProductoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class ProductoController {
    private final ProductoService productoService;

    // The Gateway forwards the request to /crear after stripping the prefix.
    // We add a print statement to verify the request reaches this method.
    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductoDto createProducto(@RequestBody ProductoDto productoDto) {
        System.out.println("Request POST received at endpoint: /crear");
        return productoService.createProducto(productoDto);
    }

    // The Gateway forwards the request for all products to /
    // We add a print statement to verify the request reaches this method.
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductoDto> getAllProductos() {
        System.out.println("Request GET received at endpoint: /");
        return productoService.getAllProductos();
    }
}
