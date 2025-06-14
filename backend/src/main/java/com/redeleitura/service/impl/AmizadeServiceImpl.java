package com.redeleitura.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.redeleitura.dto.UsuarioLivrosEmComumDTO;
import com.redeleitura.entity.LivrosLidos;
import com.redeleitura.repository.LivrosLidosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redeleitura.entity.Amizade;
import com.redeleitura.entity.Usuario;
import com.redeleitura.enums.StatusAmizade;
import com.redeleitura.repository.AmizadeRepository;
import com.redeleitura.repository.UsuarioRepository;
import com.redeleitura.service.AmizadeService;

@Service
public class AmizadeServiceImpl implements AmizadeService {

    @Autowired
    private AmizadeRepository amizadeRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private LivrosLidosRepository livrosLidosRepository;

    @Override
    public String enviarSolicitacao(Integer idSolicitante, Integer idSolicitado) {
        Usuario solicitante = usuarioRepository.findById(idSolicitante)
            .orElseThrow(() -> new RuntimeException("Solicitante não encontrado"));

        Usuario solicitado = usuarioRepository.findById(idSolicitado)
            .orElseThrow(() -> new RuntimeException("Solicitado não encontrado"));

        Optional<Amizade> existente = amizadeRepository.findRelacionamentoEntreUsuarios(
                solicitante, solicitado, Arrays.asList(StatusAmizade.values())
        );

        if (existente.isPresent()) {
            Amizade amizade = existente.get();

            if (amizade.getStatus().equals(StatusAmizade.RECUSADA)) {
                amizade.setStatus(StatusAmizade.PENDENTE);
                amizadeRepository.save(amizade);  // Faz UPDATE
            } else {
                return "Já existe uma solicitação ou amizade entre esses usuários.";
            }
        } else {
            Amizade amizade = new Amizade();
            amizade.setSolicitante(solicitante);
            amizade.setSolicitado(solicitado);
            amizade.setStatus(StatusAmizade.PENDENTE);

            amizadeRepository.save(amizade);  // Faz INSERT
        }

        return "Solicitação de amizade enviada com sucesso.";
    }

    @Override
    public String aceitarSolicitacao(Long idSolicitacao) {
        Amizade amizade = amizadeRepository.findById(idSolicitacao)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));

        if(amizade.getStatus() == StatusAmizade.PENDENTE) {
            amizade.setStatus(StatusAmizade.ACEITA);
            amizadeRepository.save(amizade);

            return "Solicitação de amizade aceita.";
        }

        return "Os usuários já são amigos ou não há uma solicitação pendente.";
    }

    @Override
    public String removerSolicitacao(Long idSolicitacao) {
        Amizade amizade = amizadeRepository.findById(idSolicitacao)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));

        if(!(amizade.getStatus() == StatusAmizade.RECUSADA)) {
            amizade.setStatus(StatusAmizade.RECUSADA);
            amizadeRepository.save(amizade);

            return "Solicitação de amizade recusada.";
        }

        return "Não há amizade nem solicitação pendente entre os usuários.";
    }

    @Override
    public List<UsuarioLivrosEmComumDTO> listarAmigos(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        List<Amizade> amizades = amizadeRepository.findByStatusAndUsuario(StatusAmizade.ACEITA, usuario);

        // Lista de amigos (apenas os objetos Usuario)
        List<Usuario> amigos = amizades.stream()
                .map(am -> am.getSolicitante().equals(usuario) ? am.getSolicitado() : am.getSolicitante())
                .toList();

        // Buscar os livros que o usuário atual leu
        List<LivrosLidos> livrosUsuarioAtual = livrosLidosRepository.findByUsuario(usuario);
        Set<String> livrosLidosIsbns = livrosUsuarioAtual.stream()
                .map(LivrosLidos::getIsbn)
                .collect(Collectors.toSet());

        // Buscar os livros em comum apenas entre o usuário e os amigos
        Map<Usuario, Long> usuarioComumLivrosMap = buscarUsuariosELivrosEmComum(livrosLidosIsbns, amigos);

        // Montar a lista de DTOs
        return usuarioComumLivrosMap.entrySet().stream()
                .map(entry -> new UsuarioLivrosEmComumDTO(
                        entry.getValue(),
                        entry.getKey().getNome(),
                        entry.getKey().getDescricao(),
                        entry.getKey().getUsuario()
                ))
                .toList();
    }

    private Map<Usuario, Long> buscarUsuariosELivrosEmComum(Set<String> livrosLidosIsbns, List<Usuario> amigos) {
        Map<Usuario, Long> resultado = new HashMap<>();

        for (Usuario amigo : amigos) {
            List<LivrosLidos> livrosDoAmigo = livrosLidosRepository.findByUsuario(amigo);

            long quantidadeEmComum = livrosDoAmigo.stream()
                    .filter(livro -> livrosLidosIsbns.contains(livro.getIsbn()))
                    .count();

            if (quantidadeEmComum > 0) {
                resultado.put(amigo, quantidadeEmComum);
            }
        }

        return resultado;
    }
}