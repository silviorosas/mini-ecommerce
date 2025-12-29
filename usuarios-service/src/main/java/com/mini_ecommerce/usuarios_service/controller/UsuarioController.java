package com.mini_ecommerce.usuarios_service.controller;

import com.mini_ecommerce.usuarios_service.dto.UsuarioDTO;
import com.mini_ecommerce.usuarios_service.entity.Usuario;
import com.mini_ecommerce.usuarios_service.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    // Cambiamos a "/crear" para mayor claridad en la URL
    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO crear(@RequestBody Usuario usuario) {
        return usuarioService.crearUsuario(usuario);
    }

    @GetMapping("/detalle/{id}")
    public UsuarioDTO obtener(@PathVariable Long id) {
        return usuarioService.obtenerUsuarioPorId(id);
    }

    @GetMapping("/listar")
    public List<UsuarioDTO> listar() {
        return usuarioService.listarUsuarios();
    }
}