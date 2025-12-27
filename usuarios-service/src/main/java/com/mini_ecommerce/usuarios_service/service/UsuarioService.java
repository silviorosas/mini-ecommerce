package com.mini_ecommerce.usuarios_service.service;


import com.mini_ecommerce.usuarios_service.dto.UsuarioDTO;
import com.mini_ecommerce.usuarios_service.entity.Usuario;

import java.util.List;


public interface UsuarioService {
    UsuarioDTO crearUsuario(Usuario usuario);
    UsuarioDTO obtenerUsuarioPorId(Long id);
    List<UsuarioDTO> listarUsuarios();
}