package com.mini_ecommerce.pagos_service.service;

import com.mini_ecommerce.pagos_service.dto.OrdenEventoDto;
import com.mini_ecommerce.pagos_service.entity.Pago;
import com.mini_ecommerce.pagos_service.repository.PagoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PagoService {

    private final PagoRepository pagoRepository;
    private final StreamBridge streamBridge;

    // Método que ya teníamos para Kafka
    // Método para Kafka
    public void procesarPago(OrdenEventoDto ordenEvento) {
        log.info("Procesando pago para orden: {}", ordenEvento.getId());

        // 1. Creamos y guardamos el pago en la base de datos
        Pago pago = Pago.builder()
                .ordenId(ordenEvento.getId())
                .monto(ordenEvento.getTotal())
                .metodoPago("TARJETA_CREDITO")
                .estado("APROBADO")
                .build();

        pagoRepository.save(pago);
        log.info("Pago guardado en BD para orden: {}", ordenEvento.getId());

        // 2. NOTIFICAR de vuelta a ordenes-service
        // IMPORTANTE: El nombre "pagos-topic-out-0" debe coincidir con tu pagos-service.yml
        // Enviamos el objeto 'pago' (o un DTO de respuesta) para que ordenes-service sepa el nuevo estado
        streamBridge.send("pagos-topic-out-0", pago);

        log.info("Evento de confirmación enviado a pagos-topic para orden: {}", ordenEvento.getId());
    }

    // NUEVO: Método para el controlador
    public List<Pago> listarPagos() {
        return pagoRepository.findAll();
    }

    public Pago buscarPorOrdenId(Long ordenId) {
        return pagoRepository.findAll().stream()
                .filter(p -> p.getOrdenId().equals(ordenId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
    }
}