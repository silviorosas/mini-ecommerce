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

    @Autowired
    private final StreamBridge streamBridge;

    @Override
    public OrdenDto createOrden(OrdenDto ordenDto) {
        // Mapeo de DTO a Entidad
        List<DetalleOrden> detalles = ordenDto.getDetalles().stream()
                .map(detalleDto -> DetalleOrden.builder()
                        .idProducto(detalleDto.getIdProducto())
                        .cantidad(detalleDto.getCantidad())
                        .precioUnitario(detalleDto.getPrecioUnitario())
                        .build())
                .collect(Collectors.toList());

        Orden orden = Orden.builder()
                .idUsuario(ordenDto.getIdUsuario())
                .fechaCreacion(new Date())
                .estado("CREADA")
                .detalles(detalles)
                .build();

        detalles.forEach(detalle -> detalle.setOrden(orden));

        Orden savedOrden = ordenRepository.save(orden);

        // Nuevo código: Mapea la entidad a un DTO de evento y lo publica en Kafka
        OrdenEventoDto evento = OrdenEventoDto.builder()
                .id(savedOrden.getId())
                .idUsuario(savedOrden.getIdUsuario())
                .estado(savedOrden.getEstado())
                .build();

        streamBridge.send("ordenes-topic", evento);

        return OrdenDto.builder()
                .id(savedOrden.getId())
                .idUsuario(savedOrden.getIdUsuario())
                .estado(savedOrden.getEstado())
                .detalles(ordenDto.getDetalles())
                .build();
    }
}