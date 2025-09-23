package com.mini_ecommerce.productos_service.repository;

import com.mini_ecommerce.productos_service.entity.Producto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductoRepository extends MongoRepository<Producto,String> {
}
