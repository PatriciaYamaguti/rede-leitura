package com.redeleitura.controller;

import java.util.List;

import com.redeleitura.dto.LivroAtualDTO;
import com.redeleitura.dto.LivrosLidosDTO;
import com.redeleitura.mapper.LivroMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.redeleitura.entity.LivroAtual;
import com.redeleitura.entity.LivrosLidos;
import com.redeleitura.service.livro.LivroServiceImpl;
import com.redeleitura.util.GoogleBooksUtil;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    private final LivroServiceImpl livroServiceImpl;
    private final GoogleBooksUtil googleBooksUtil;
    private final LivroMapper livroMapper;

    public LivroController(LivroServiceImpl livroServiceImpl, GoogleBooksUtil googleBooksUtil, LivroMapper livroMapper) {
        this.livroServiceImpl = livroServiceImpl;
        this.googleBooksUtil = googleBooksUtil;
        this.livroMapper = livroMapper;
    }

    @PostMapping("/{idUsuario}/lido/{isbn}")
    public ResponseEntity<LivrosLidosDTO> marcarComoLido(@PathVariable Integer idUsuario, @PathVariable String isbn) {
        LivrosLidos livroLido = livroServiceImpl.marcarLivroComoLido(idUsuario, isbn);
        LivrosLidosDTO dto = livroMapper.toLivrosLidosDTO(livroLido);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/{idUsuario}/atual/{isbn}")
    public ResponseEntity<LivroAtualDTO> definirLivroAtual(@PathVariable Integer idUsuario, @PathVariable String isbn) {
        LivroAtual livroAtual = livroServiceImpl.definirLivroAtual(idUsuario, isbn);
        LivroAtualDTO dto = livroMapper.toLivroAtualDTO(livroAtual);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<GoogleBooksUtil.LivroDTO>> buscarLivrosPorTitulo(@RequestParam String titulo) { 
        List<GoogleBooksUtil.LivroDTO> resultados = googleBooksUtil.buscarLivrosPorTitulo(titulo);
        return ResponseEntity.ok(resultados); 
    }
    
}

