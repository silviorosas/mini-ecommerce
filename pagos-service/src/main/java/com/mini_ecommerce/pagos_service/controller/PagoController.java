package com.mini_ecommerce.pagos_service.controller;

import com.mini_ecommerce.pagos_service.entity.Pago;
import com.mini_ecommerce.pagos_service.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;

    @GetMapping
    public ResponseEntity<List<Pago>> obtenerTodos() {
        return ResponseEntity.ok(pagoService.listarPagos());
    }

    @GetMapping("/orden/{ordenId}")
    public ResponseEntity<Pago> obtenerPorOrden(@PathVariable Long ordenId) {
        return ResponseEntity.ok(pagoService.buscarPorOrdenId(ordenId));
    }
}