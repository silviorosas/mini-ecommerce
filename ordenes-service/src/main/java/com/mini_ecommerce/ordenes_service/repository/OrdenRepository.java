package com.mini_ecommerce.ordenes_service.repository;

import com.mini_ecommerce.ordenes_service.entity.Orden;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdenRepository extends JpaRepository<Orden, Long> {
}
