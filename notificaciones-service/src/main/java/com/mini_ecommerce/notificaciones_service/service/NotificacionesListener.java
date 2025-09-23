package com.mini_ecommerce.notificaciones_service.service;

import com.mini_ecommerce.notificaciones_service.dto.OrdenEventoDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class NotificacionesListener {

    private static final Logger log = LoggerFactory.getLogger(NotificacionesListener.class);

    @Bean
    public Consumer<OrdenEventoDto> ordenes() {
        return ordenEventoDto -> {
            log.info("--- NUEVA NOTIFICACION DE ORDEN ---");
            log.info("ID de la Orden: {}", ordenEventoDto.getId());
            log.info("ID del Usuario: {}", ordenEventoDto.getIdUsuario());
            log.info("Estado de la Orden: {}", ordenEventoDto.getEstado());
            log.info("----------------------------------");
            // Aquí puedes agregar la lógica para enviar correos, SMS, etc.
        };
    }
}
