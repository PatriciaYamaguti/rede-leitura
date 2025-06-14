package com.redeleitura.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redeleitura.dto.UsuarioDTO;
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

    @Override
    public String enviarSolicitacao(Integer idSolicitante, Integer idSolicitado) {
        Usuario solicitante = usuarioRepository.findById(idSolicitante)
            .orElseThrow(() -> new RuntimeException("Solicitante não encontrado"));

        Usuario solicitado = usuarioRepository.findById(idSolicitado)
            .orElseThrow(() -> new RuntimeException("Solicitado não encontrado"));

        Optional<Amizade> existente = amizadeRepository.findRelacionamentoEntreUsuarios(
                solicitante, solicitado, List.of(StatusAmizade.PENDENTE, StatusAmizade.ACEITA)
        );

        if (existente.isPresent()) {
            return "Já existe uma solicitação ou amizade entre esses usuários.";
        }

        Amizade amizade = new Amizade();
        amizade.setSolicitante(solicitante);
        amizade.setSolicitado(solicitado);
        amizade.setStatus(StatusAmizade.PENDENTE);

        amizadeRepository.save(amizade);

        return "Solicitação de amizade enviada com sucesso.";
    }
    @Override
    public String aceitarSolicitacao(Long idSolicitacao) {
        Amizade amizade = amizadeRepository.findById(idSolicitacao)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));

        amizade.setStatus(StatusAmizade.ACEITA);
        amizadeRepository.save(amizade);

        return "Solicitação de amizade aceita.";
    }

    @Override
    public String removerSolicitacao(Long idSolicitacao) {
        return null;
    }

    @Override
    public List<UsuarioDTO> listarAmigos() {
        return null;
    }

}