package com.mini_ecommerce.ordenes_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagoStatusResponse {
    private String estado;
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}