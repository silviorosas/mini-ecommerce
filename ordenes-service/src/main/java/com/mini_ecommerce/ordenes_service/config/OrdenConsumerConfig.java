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
            System.out.println("Recibida confirmación. ID Orden: " + pago.getOrdenId() + " Estado: " + pago.getEstado());

            ordenRepository.findById(pago.getOrdenId()).ifPresent(orden -> {
                // CAMBIO: Evalúa si el estado es "PAGADA", que es lo que envía el PagoService
                if ("PAGADA".equals(pago.getEstado())) {
                    orden.setEstado("PAGADA");
                } else {
                    orden.setEstado("PAGO_RECHAZADO");
                }
                ordenRepository.save(orden);
                System.out.println("¡Orden " + orden.getId() + " actualizada con éxito!");
            });
        };
    }
}
