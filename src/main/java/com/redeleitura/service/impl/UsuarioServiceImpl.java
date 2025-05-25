package com.redeleitura.service.impl;

import com.redeleitura.dto.UsuarioDTO;
import com.redeleitura.entity.Acesso;
import com.redeleitura.entity.Usuario;
import com.redeleitura.repository.AcessoRepository;
import com.redeleitura.repository.UsuarioRepository;
import com.redeleitura.service.UsuarioService;
import com.redeleitura.util.HashUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AcessoRepository acessoRepository;

    @Override
    @Transactional
    public Usuario cadastrarUsuario(UsuarioDTO usuarioDTO) {
        if (usuarioRepository.findByUsuario(usuarioDTO.getUsuario()).isPresent()) {
            throw new IllegalArgumentException("Usuário já cadastrado");
        }

        String senhaHash = HashUtil.gerarHashSHA256(usuarioDTO.getAcesso().getSenha());

        Usuario usuario = new Usuario(
                usuarioDTO.getNome(),
                usuarioDTO.getUsuario(),
                usuarioDTO.getLivroAtualIsbn()
        );

        Acesso acesso = new Acesso(usuario, "USER", senhaHash);
        usuario.setAcesso(acesso);

        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        acessoRepository.save(acesso);
        return usuarioSalvo;
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return List.of();
    }

    @Override
    public Optional<Usuario> buscarUsuarioPorId(String id) {
        return Optional.empty();
    }

    @Override
    public Usuario atualizarUsuario(Long id, UsuarioDTO usuarioDTO) {
        return null;
    }

    @Override
    public void deletarUsuario(Long id) {

    }
}
