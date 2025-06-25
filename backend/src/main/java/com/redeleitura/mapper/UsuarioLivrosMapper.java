package com.redeleitura.mapper;

import org.springframework.stereotype.Component;

import com.redeleitura.dto.LivroAtualDTO;
import com.redeleitura.dto.UsuarioLivrosDTO;
import com.redeleitura.entity.Usuario;

@Component
public class UsuarioLivrosMapper {
    public UsuarioLivrosDTO toUsuarioLivrosDTO(Usuario usuario) {
        if (usuario == null) return null;

        UsuarioLivrosDTO dto = new UsuarioLivrosDTO();
        dto.setNome(usuario.getNome());
        dto.setUsuario(usuario.getUsuario());
        dto.setDescricao(usuario.getDescricao());
        dto.setDataCadastro(usuario.getDataCadastro());
        dto.setId(usuario.getId());

        if (usuario.getLivroAtual() != null) {
            LivroAtualDTO livroAtualDTO = new LivroAtualDTO();
            livroAtualDTO.setIsbn(usuario.getLivroAtual().getIsbn());
            livroAtualDTO.setTitulo(usuario.getLivroAtual().getTitulo());
            livroAtualDTO.setAutor(usuario.getLivroAtual().getAutor());
            dto.setLivroAtual(livroAtualDTO);
        }

        return dto;
    }
}
