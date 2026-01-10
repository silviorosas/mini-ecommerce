package com.mini_ecommerce.ordenes_service.service;

import com.mini_ecommerce.ordenes_service.dto.OrdenDto;
import com.mini_ecommerce.ordenes_service.dto.OrdenEventoDto;
import com.mini_ecommerce.ordenes_service.entity.DetalleOrden;
import com.mini_ecommerce.ordenes_service.entity.Orden;
import com.mini_ecommerce.ordenes_service.repository.OrdenRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrdenServiceImpl implements OrdenService {
    private final OrdenRepository ordenRepository;
    private final StreamBridge streamBridge; // Eliminado @Autowired innecesario por @AllArgsConstructor

    @Override
    public OrdenDto createOrden(OrdenDto ordenDto) {
        // 1. Mapeo y cálculo del total
        List<DetalleOrden> detalles = ordenDto.getDetalles().stream()
                .map(detalleDto -> DetalleOrden.builder()
                        .idProducto(detalleDto.getIdProducto())
                        .cantidad(detalleDto.getCantidad())
                        .precioUnitario(detalleDto.getPrecioUnitario())
                        .build())
                .collect(Collectors.toList());

        // Calculamos el total sumando (cantidad * precio) de cada detalle
        java.math.BigDecimal totalCalculado = detalles.stream()
                .map(d -> d.getPrecioUnitario().multiply(java.math.BigDecimal.valueOf(d.getCantidad())))
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);

        // 2. Construcción de la Entidad
        Orden orden = Orden.builder()
                .idUsuario(ordenDto.getIdUsuario())
                .fechaCreacion(new Date())
                .estado("PENDIENTE_PAGO") // Recomendación profesional: Iniciar como pendiente
                .detalles(detalles)
                .build();

        detalles.forEach(detalle -> detalle.setOrden(orden));

        // 3. Persistencia
        Orden savedOrden = ordenRepository.save(orden);

        // 4. Publicación del evento con el TOTAL incluido
        OrdenEventoDto evento = OrdenEventoDto.builder()
                .id(savedOrden.getId())
                .idUsuario(savedOrden.getIdUsuario())
                .total(totalCalculado) // <--- El campo que necesitábamos
                .estado(savedOrden.getEstado())
                .build();

        streamBridge.send("ordenes-topic", evento);

        // 5. Retorno del DTO
        return OrdenDto.builder()
                .id(savedOrden.getId())
                .idUsuario(savedOrden.getIdUsuario())
                .estado(savedOrden.getEstado())
                .detalles(ordenDto.getDetalles())
                .build();
    }
}