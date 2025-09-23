package com.mini_ecommerce.ordenes_service.service;

import com.mini_ecommerce.ordenes_service.dto.OrdenDto;

public interface OrdenService {
    OrdenDto createOrden(OrdenDto ordenDto);
}