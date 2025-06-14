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
                solicitante, solicitado, List.of(StatusAmizade.PENDENTE, StatusAmizade.ACEITA, StatusAmizade.RECUSADA)
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
    public List<UsuarioDTO> listarAmigos() {
        return null;
    }

}