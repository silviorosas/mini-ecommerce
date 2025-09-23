package com.mini_ecommerce.notificaciones_service.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrdenEventoDto {
    private Long id;
    private Long idUsuario;
    private String estado;
}