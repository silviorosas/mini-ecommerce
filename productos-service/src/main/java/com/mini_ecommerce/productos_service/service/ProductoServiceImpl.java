package com.mini_ecommerce.productos_service.service;

import com.mini_ecommerce.productos_service.dto.ProductoDto;
import com.mini_ecommerce.productos_service.entity.Producto;
import com.mini_ecommerce.productos_service.repository.ProductoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    @Override
    public ProductoDto createProducto(ProductoDto productoDto) {
        Producto producto = Producto.builder()
                .nombre(productoDto.getNombre())
                .descripcion(productoDto.getDescripcion())
                .precio(productoDto.getPrecio())
                .stock(productoDto.getStock())
                .build();
        Producto savedProducto = productoRepository.save(producto);
        return ProductoDto.builder()
                .id(savedProducto.getId())
                .nombre(savedProducto.getNombre())
                .descripcion(savedProducto.getDescripcion())
                .precio(savedProducto.getPrecio())
                .stock(savedProducto.getStock())
                .build();
    }

    @Override
    public List<ProductoDto> getAllProductos() {
        return productoRepository.findAll().stream()
                .map(producto -> ProductoDto.builder()
                        .id(producto.getId())
                        .nombre(producto.getNombre())
                        .descripcion(producto.getDescripcion())
                        .precio(producto.getPrecio())
                        .stock(producto.getStock())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public ProductoDto getProductoById(String id) {
        return productoRepository.findById(id)
                .map(producto -> ProductoDto.builder()
                        .id(producto.getId())
                        .nombre(producto.getNombre())
                        .descripcion(producto.getDescripcion())
                        .precio(producto.getPrecio())
                        .stock(producto.getStock())
                        .build())
                .orElse(null);
    }
}
