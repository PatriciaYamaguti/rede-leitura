package com.redeleitura.mapper;

import com.redeleitura.dto.LivroAtualDTO;
import com.redeleitura.dto.LivrosLidosDTO;
import com.redeleitura.entity.LivroAtual;
import com.redeleitura.entity.LivrosLidos;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LivroMapper {

    private final UsuarioMapper usuarioMapper;

    public LivroMapper(UsuarioMapper usuarioMapper) {
        this.usuarioMapper = usuarioMapper;
    }

    public LivroAtualDTO toLivroAtualDTO(LivroAtual livroAtual) {
        if (livroAtual == null) return null;

        LivroAtualDTO dto = new LivroAtualDTO();
        dto.setIsbn(livroAtual.getIsbn());
        dto.setTitulo(livroAtual.getTitulo());
        dto.setAutor(livroAtual.getAutor());
        dto.setUsuarioDTO(usuarioMapper.toUsuarioDTO(livroAtual.getUsuario()));
        return dto;
    }

    public LivrosLidosDTO toLivrosLidosDTO(LivrosLidos livrosLidos) {
        if (livrosLidos == null) return null;

        LivrosLidosDTO dto = new LivrosLidosDTO();
        dto.setIsbn(livrosLidos.getIsbn());
        dto.setTitulo(livrosLidos.getTitulo());
        dto.setAutor(livrosLidos.getAutor());
        dto.setDataLeitura(livrosLidos.getDataLeitura());
        dto.setUsuarioDTO(usuarioMapper.toUsuarioDTO(livrosLidos.getUsuario()));
        return dto;
    }

    public List<LivrosLidosDTO> toLivrosLidosDTO(List<LivrosLidos> livrosLidos) {
        if (livrosLidos == null) return null;

        List<LivrosLidosDTO> dtos = new ArrayList<>();

        livrosLidos.forEach(livro -> {
            LivrosLidosDTO dto = new LivrosLidosDTO();
            dto.setIsbn(livro.getIsbn());
            dto.setTitulo(livro.getTitulo());
            dto.setAutor(livro.getAutor());
            dto.setDataLeitura(livro.getDataLeitura());
            dto.setUsuarioDTO(usuarioMapper.toUsuarioDTO(livro.getUsuario()));
            dtos.add(dto);
        });

        return dtos;
    }

    public LivroAtual toLivroAtualEntity(LivroAtualDTO dto) {
        if (dto == null) return null;

        LivroAtual livroAtual = new LivroAtual();
        livroAtual.setIsbn(dto.getIsbn());
        livroAtual.setTitulo(dto.getTitulo());
        livroAtual.setAutor(dto.getAutor());
        livroAtual.setUsuario(usuarioMapper.toUsuarioEntity(dto.getUsuarioDTO()));
        return livroAtual;
    }

    public LivrosLidos toLivrosLidosEntity(LivrosLidosDTO dto) {
        if (dto == null) return null;

        LivrosLidos livrosLidos = new LivrosLidos();
        livrosLidos.setIsbn(dto.getIsbn());
        livrosLidos.setTitulo(dto.getTitulo());
        livrosLidos.setAutor(dto.getAutor());
        livrosLidos.setDataLeitura(dto.getDataLeitura());
        livrosLidos.setUsuario(usuarioMapper.toUsuarioEntity(dto.getUsuarioDTO()));
        return livrosLidos;
    }
}