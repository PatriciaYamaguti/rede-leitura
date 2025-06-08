package com.redeleitura.service.impl;

import com.redeleitura.dto.UsuarioComInteresseDTO;
import com.redeleitura.dto.UsuarioDTO;
import com.redeleitura.entity.Acesso;
import com.redeleitura.entity.LivrosLidos;
import com.redeleitura.entity.Usuario;
import com.redeleitura.mapper.UsuarioMapper;
import com.redeleitura.repository.AcessoRepository;
import com.redeleitura.repository.LivroAtualRepository;
import com.redeleitura.repository.LivrosLidosRepository;
import com.redeleitura.repository.UsuarioRepository;
import com.redeleitura.service.UsuarioService;
import com.redeleitura.util.HashUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AcessoRepository acessoRepository;

    @Autowired
    private LivroAtualRepository livroAtualRepository;

    @Autowired
    private LivrosLidosRepository livrosLidosRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Override
    @Transactional
    public Usuario cadastrarUsuario(UsuarioDTO usuarioDTO) {
        if (usuarioRepository.findByUsuario(usuarioDTO.getUsuario()).isPresent()) {
            throw new IllegalArgumentException("Usuário já cadastrado");
        }

        String senhaHash = HashUtil.gerarHashSHA256(usuarioDTO.getAcesso().getSenha());

        Usuario usuario = new Usuario(
                usuarioDTO.getNome(),
                usuarioDTO.getUsuario()
        );

        Acesso acesso = new Acesso(usuario, "USER", senhaHash);
        usuario.setAcesso(acesso);

        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        acessoRepository.save(acesso);
        return usuarioSalvo;
    }

    @Override
    public List<UsuarioComInteresseDTO> listarUsuariosPorInteresses(Integer idUsuario) {
        Usuario usuarioAtual = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        List<LivrosLidos> livrosUsuarioAtual = livrosLidosRepository.findByUsuario(usuarioAtual);
        Set<String> livrosLidosIsbns = livrosUsuarioAtual.stream()
                .map(LivrosLidos::getIsbn)
                .collect(Collectors.toSet());

        List<Usuario> outrosUsuarios = usuarioRepository.findAll().stream()
                .filter(u -> !u.getId().equals(usuarioAtual.getId()))
                .toList();

        Map<Usuario, Long> usuarioComumLivrosMap = new HashMap<>();

        for (Usuario u : outrosUsuarios) {
            List<LivrosLidos> livrosDoUsuario = livrosLidosRepository.findByUsuario(u);
            long countComum = livrosDoUsuario.stream()
                    .map(LivrosLidos::getIsbn)
                    .filter(livrosLidosIsbns::contains)
                    .count();
            usuarioComumLivrosMap.put(u, countComum);
        }

        return usuarioComumLivrosMap.entrySet().stream()
                .sorted(Map.Entry.<Usuario, Long>comparingByValue(Comparator.reverseOrder()))
                .map(entry -> {
                    UsuarioDTO dto = usuarioMapper.toUsuarioDTO(entry.getKey());
                    return new UsuarioComInteresseDTO(dto, entry.getValue());
                })
                .collect(Collectors.toList());
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
