package com.mini_ecommerce.pagos_service.config;

import com.mini_ecommerce.pagos_service.dto.OrdenEventoDto;
import com.mini_ecommerce.pagos_service.service.PagoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class PagoConsumerConfig {

    @Bean
    public Consumer<OrdenEventoDto> consumirOrden(PagoService pagoService) {
        return event -> {
            pagoService.procesarPago(event);
        };
    }
}