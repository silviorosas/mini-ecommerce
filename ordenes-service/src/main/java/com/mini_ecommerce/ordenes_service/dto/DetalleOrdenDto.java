package com.mini_ecommerce.ordenes_service.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetalleOrdenDto {
    private Long idProducto;
    private Integer cantidad;
    private BigDecimal precioUnitario;
}