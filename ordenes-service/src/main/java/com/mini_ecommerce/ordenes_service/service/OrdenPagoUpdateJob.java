package com.mini_ecommerce.ordenes_service.service;

import com.mini_ecommerce.ordenes_service.dto.PagoStatusResponse;
import com.mini_ecommerce.ordenes_service.repository.OrdenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdenPagoUpdateJob {

    private final OrdenRepository ordenRepository;
    private final RestTemplate restTemplate; // Necesario para preguntar a pagos-service

    // Se ejecuta cada 5 minutos para pruebas (en producción sería una vez al día)
    @Scheduled(cron = "0 18 22 * * ?", zone = "America/Argentina/Buenos_Aires")
    public void ejecutarContingencia() {
        log.info("Iniciando Job de Contingencia: Buscando órdenes pendientes...");

        // 1. Buscamos todas las órdenes que sigan en PENDIENTE_PAGO
        ordenRepository.findByEstado("PENDIENTE_PAGO").forEach(orden -> {
            try {
                log.info("Verificando estado de pago para Orden ID: {}", orden.getId());

                // 2. Consultamos directamente al microservicio de pagos vía HTTP
                // Usamos el nombre del servicio gracias a Eureka
                String url = "http://pagos-service/api/pagos/orden/" + orden.getId();

                // Suponemos que pagos-service devuelve un objeto con el estado
                // Si el pago existe y está APROBADO, actualizamos la orden
                PagoStatusResponse pago = restTemplate.getForObject(url, PagoStatusResponse.class);

                if (pago != null && "APROBADO".equals(pago.getEstado())) {
                    orden.setEstado("PAGADA");
                    ordenRepository.save(orden);
                    log.info("✔ Orden {} sincronizada con éxito a PAGADA", orden.getId());
                }
            } catch (Exception e) {
                log.error("No se pudo verificar la orden {}: {}", orden.getId(), e.getMessage());
            }
        });
    }
}
