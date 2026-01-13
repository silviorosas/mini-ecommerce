package com.mini_ecommerce.notificaciones_service.config;

import com.mini_ecommerce.notificaciones_service.dto.OrdenEventoDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class NotificacionConsumerConfig {

    @Bean
    public Consumer<OrdenEventoDto> consumirNotificacion() {
        return evento -> {
            if ("PAGADA".equals(evento.getEstado())) {
                System.out.println("---------------------------------------------------------");
                System.out.println("NOTIFICACIÓN ENVIADA: ");
                System.out.println("Estimado usuario " + evento.getIdUsuario() + ",");
                System.out.println("Su orden #" + evento.getId() + " ha sido procesada con éxito.");
                System.out.println("---------------------------------------------------------");
            }
        };
    }
}
