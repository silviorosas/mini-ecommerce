package com.mini_ecommerce.ordenes_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrdenEventoDto {
    private Long id;
    private Long idUsuario;
    private BigDecimal total; // <--- AGREGAR ESTO
    private String estado;
}