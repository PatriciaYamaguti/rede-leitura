package com.redeleitura.mapper;

import com.redeleitura.dto.AcessoDTO;
import com.redeleitura.dto.LivroAtualDTO;
import com.redeleitura.dto.UsuarioDTO;
import com.redeleitura.entity.Acesso;
import com.redeleitura.entity.LivroAtual;
import com.redeleitura.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioDTO toUsuarioDTO(Usuario usuario) {
        if (usuario == null) return null;

        UsuarioDTO dto = new UsuarioDTO();
        dto.setNome(usuario.getNome());
        dto.setUsuario(usuario.getUsuario());
        dto.setDescricao(usuario.getDescricao());
        dto.setAcesso(toAcessoDTO(usuario.getAcesso()));
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

    public Usuario toUsuarioEntity(UsuarioDTO dto) {
        if (dto == null) return null;

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setUsuario(dto.getUsuario());
        usuario.setDescricao(dto.getDescricao());
        usuario.setAcesso(toAcessoEntity(dto.getAcesso()));
        usuario.setDataCadastro(dto.getDataCadastro());
        usuario.setId(dto.getId());

        if (dto.getLivroAtual() != null) {
            LivroAtual livroAtual = new LivroAtual();
            livroAtual.setIsbn(dto.getLivroAtual().getIsbn());
            livroAtual.setTitulo(dto.getLivroAtual().getTitulo());
            livroAtual.setAutor(dto.getLivroAtual().getAutor());
            livroAtual.setUsuario(usuario);
            usuario.setLivroAtual(livroAtual);
        }

        return usuario;
    }

    private AcessoDTO toAcessoDTO(Acesso acesso) {
        if (acesso == null) return null;

        AcessoDTO dto = new AcessoDTO();
        dto.setTipoAcesso(acesso.getTipoAcesso());
        dto.setSenha(acesso.getSenha());
        return dto;
    }

    private Acesso toAcessoEntity(AcessoDTO dto) {
        if (dto == null) return null;

        Acesso acesso = new Acesso();
        acesso.setTipoAcesso(dto.getTipoAcesso());
        acesso.setSenha(dto.getSenha());
        return acesso;
    }
}