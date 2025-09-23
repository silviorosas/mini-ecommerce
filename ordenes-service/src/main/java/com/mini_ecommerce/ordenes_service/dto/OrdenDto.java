package com.mini_ecommerce.ordenes_service.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrdenDto {
    private Long id;
    private Long idUsuario;
    private String estado;
    private List<DetalleOrdenDto> detalles;
}
