package com.mini_ecommerce.ordenes_service.controller;

import com.mini_ecommerce.ordenes_service.dto.OrdenDto;
import com.mini_ecommerce.ordenes_service.service.OrdenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ordenes")
@AllArgsConstructor
public class OrdenController {
    private final OrdenService ordenService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrdenDto crearOrden(@RequestBody OrdenDto ordenDto) {
        return ordenService.createOrden(ordenDto);
    }

    @GetMapping
    public String probar(){
        return "Prueba de listar ordenes";
    }
}