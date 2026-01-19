package com.mini_ecommerce.ordenes_service.repository;

import com.mini_ecommerce.ordenes_service.entity.Orden;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrdenRepository extends JpaRepository<Orden, Long> {
    // Este es el método que necesitas para el Job
    List<Orden> findByEstado(String estado);

    // Este es el necesario para el Job inteligente
    List<Orden> findByEstadoAndFechaCreacionBefore(String estado, LocalDateTime fecha);
}
