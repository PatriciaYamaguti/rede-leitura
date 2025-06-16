package com.redeleitura.controller;

import com.redeleitura.dto.UsuarioLivrosEmComumDTO;
import com.redeleitura.dto.UsuarioDTO;
import com.redeleitura.service.usuario.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioServiceImpl usuarioService;

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.cadastrarUsuario(usuarioDTO);
    }

    @GetMapping("/interesses/{idUsuario}")
    public ResponseEntity<List<UsuarioLivrosEmComumDTO>> listarPorInteresse(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(usuarioService.listarUsuariosPorInteresses(idUsuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.atualizarUsuario(id, usuarioDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable Integer id) {
        return usuarioService.deletarUsuario(id);
    }

    @PostMapping("/logar")
    public ResponseEntity<?> logar(@RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.logarUsuario(usuarioDTO);
    }
}
