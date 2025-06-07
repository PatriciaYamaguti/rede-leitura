package com.redeleitura.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.redeleitura.entity.LivroAtual;
import com.redeleitura.entity.LivrosLidos;
import com.redeleitura.service.LivroService;
import com.redeleitura.util.GoogleBooksUtil;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    private final LivroService livroService;
    private final GoogleBooksUtil googleBooksUtil;

    public LivroController(LivroService livroService, GoogleBooksUtil googleBooksUtil) {
        this.livroService = livroService;
        this.googleBooksUtil = googleBooksUtil;
    }

    @PostMapping("/{idUsuario}/lido/{isbn}")
    public ResponseEntity<LivrosLidos> marcarComoLido(@PathVariable Integer idUsuario, @PathVariable String isbn) {
        LivrosLidos livroLido = livroService.marcarLivroComoLido(idUsuario, isbn);
        return ResponseEntity.ok(livroLido);
    }

    @PostMapping("/{idUsuario}/atual/{isbn}")
    public ResponseEntity<LivroAtual> definirLivroAtual(@PathVariable Integer idUsuario, @PathVariable String isbn) {
        LivroAtual livroAtual = livroService.definirLivroAtual(idUsuario, isbn);
        return ResponseEntity.ok(livroAtual);
    }

     @GetMapping("/buscar")
    public ResponseEntity<List<GoogleBooksUtil.LivroDTO>> buscarLivrosPorTitulo(@RequestParam String titulo) { 
        List<GoogleBooksUtil.LivroDTO> resultados = googleBooksUtil.buscarLivrosPorTitulo(titulo);
        return ResponseEntity.ok(resultados); 
    }
    
}

