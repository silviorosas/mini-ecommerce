package com.mini_ecommerce.pagos_service.repository;

import com.mini_ecommerce.pagos_service.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepository extends JpaRepository <Pago,Long> {
}
