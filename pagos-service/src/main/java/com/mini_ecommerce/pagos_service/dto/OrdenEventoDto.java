package com.mini_ecommerce.pagos_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrdenEventoDto {
    private Long id;
    private Long idUsuario;
    private BigDecimal total; // Asegúrate de enviarlo desde Ordenes
    private String estado;
}
