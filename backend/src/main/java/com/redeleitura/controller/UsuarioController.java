package com.redeleitura.controller;

import com.redeleitura.dto.UsuarioDTO;
import com.redeleitura.entity.Usuario;
import com.redeleitura.service.UsuarioService;
import com.redeleitura.service.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioServiceImpl usuarioService;

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            Usuario usuario = usuarioService.cadastrarUsuario(usuarioDTO);
            return ResponseEntity.ok(usuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
