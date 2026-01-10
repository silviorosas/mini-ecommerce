package com.mini_ecommerce.ordenes_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true) // <-- CLAVE: Ignora id, monto y metodoPago que vienen de Pagos
public class PagoResponseDto {
    private Long ordenId;
    private String estado;
}