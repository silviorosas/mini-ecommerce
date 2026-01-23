package com.mini_ecommerce.ordenes_service.service;

import com.mini_ecommerce.ordenes_service.dto.PagoStatusResponse;
import com.mini_ecommerce.ordenes_service.entity.Orden;
import com.mini_ecommerce.ordenes_service.repository.OrdenRepository;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdenPagoUpdateJob {

    private final OrdenRepository ordenRepository;
    private final RestTemplate restTemplate;
    private final CircuitBreakerRegistry circuitBreakerRegistry;

    @Scheduled(fixedDelay = 10000)
    public void ejecutarContingencia() {
        log.info("▶▶▶ INICIANDO RECONCILIACIÓN INTELIGENTE ◀◀◀");

        CircuitBreaker pagosCircuitBreaker = circuitBreakerRegistry.circuitBreaker("pagosCB");

        LocalDateTime umbral = LocalDateTime.now().minusMinutes(5);
        List<Orden> pendientes = ordenRepository.findByEstadoAndFechaCreacionBefore("PENDIENTE_PAGO", umbral);

        if (pendientes.isEmpty()) {
            log.info("i No hay órdenes estancadas para procesar.");
            return;
        }

        pendientes.forEach(orden -> {
            log.info("[CB DEBUG] Estado: {} | Fallos: {} | Mínimo requerido: {}",
                    pagosCircuitBreaker.getState(),
                    pagosCircuitBreaker.getMetrics().getNumberOfFailedCalls(),
                    pagosCircuitBreaker.getCircuitBreakerConfig().getMinimumNumberOfCalls());

            try {
                log.info("Verificando Orden #{}", orden.getId());

                PagoStatusResponse pago = pagosCircuitBreaker.executeSupplier(() -> {
                    String url = "http://pagos-service/api/pagos/orden/" + orden.getId();
                    return restTemplate.getForObject(url, PagoStatusResponse.class);
                });

                if (pago != null && "APROBADO".equals(pago.getEstado())) {
                    orden.setEstado("PAGADA");
                    ordenRepository.save(orden);
                    log.info("✔ ÉXITO: Orden #{} sincronizada correctamente.", orden.getId());
                }

            } catch (CallNotPermittedException e) {
                log.error("🛑 FUSIBLE ABIERTO: El servicio de pagos está fallando. Saltando Orden #{} para proteger el sistema.", orden.getId());
            } catch (Exception e) {
                log.error("❌ ERROR en Orden #{}: {}", orden.getId(), e.getMessage());
            }
        });

        log.info("▶▶▶ FIN DEL PROCESO DE CONTINGENCIA ◀◀◀");
    }
}