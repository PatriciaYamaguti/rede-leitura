package com.redeleitura.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.redeleitura.dto.UsuarioDTO;
import com.redeleitura.dto.UsuarioLivrosEmComumDTO;
import com.redeleitura.service.usuario.UsuarioServiceImpl;

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

    @GetMapping
    public ResponseEntity<?> buscarUsuarioPorId(@RequestParam Integer idUsuario) {
        return usuarioService.buscarUsuarioPorId(idUsuario);
    }
}
