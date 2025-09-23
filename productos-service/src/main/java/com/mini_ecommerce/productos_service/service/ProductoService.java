package com.mini_ecommerce.productos_service.service;

import com.mini_ecommerce.productos_service.dto.ProductoDto;

import java.util.List;

public interface ProductoService {
    ProductoDto createProducto(ProductoDto productoDto);
    List<ProductoDto> getAllProductos();
    ProductoDto getProductoById(String id);
}
