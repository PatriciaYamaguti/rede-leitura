package com.redeleitura.controller;

import com.redeleitura.entity.LivrosLidos;
import com.redeleitura.entity.Usuario;
import com.redeleitura.service.LivroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @PostMapping("/{idUsuario}/lido/{isbn}")
    public ResponseEntity<LivrosLidos> marcarComoLido(@PathVariable Integer idUsuario, @PathVariable String isbn) {
        LivrosLidos livroLido = livroService.marcarLivroComoLido(idUsuario, isbn);
        return ResponseEntity.ok(livroLido);
    }

    @PostMapping("/{idUsuario}/atual/{isbn}")
    public ResponseEntity<Usuario> definirLivroAtual(@PathVariable Integer idUsuario, @PathVariable String isbn) {
        Usuario usuario = livroService.definirLivroAtual(idUsuario, isbn);
        return ResponseEntity.ok(usuario);
    }
}

