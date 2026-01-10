package com.mini_ecommerce.ordenes_service.config;

import com.mini_ecommerce.ordenes_service.dto.PagoResponseDto;
import com.mini_ecommerce.ordenes_service.repository.OrdenRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.function.Consumer;

@Configuration
public class OrdenConsumerConfig {

    private final OrdenRepository ordenRepository;

    public OrdenConsumerConfig(OrdenRepository ordenRepository) {
        this.ordenRepository = ordenRepository;
    }

    @Bean
    public Consumer<PagoResponseDto> consumirRespuestaPago() {
        return pago -> {
            System.out.println("Recibida confirmación de pago para Orden ID: " + pago.getOrdenId());

            ordenRepository.findById(pago.getOrdenId()).ifPresent(orden -> {
                if ("APROBADO".equals(pago.getEstado())) {
                    orden.setEstado("PAGADA");
                } else {
                    orden.setEstado("PAGO_RECHAZADO");
                }
                ordenRepository.save(orden);
                System.out.println("Orden " + orden.getId() + " actualizada a estado: " + orden.getEstado());
            });
        };
    }
}
