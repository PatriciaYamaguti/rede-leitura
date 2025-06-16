package com.redeleitura.service.amizade;

import java.util.*;

import com.redeleitura.dto.UsuarioLivrosEmComumDTO;
import com.redeleitura.mapper.UsuarioLivrosEmComumMapper;
import com.redeleitura.repository.LivrosLidosRepository;
import com.redeleitura.service.livro.LivrosEmComumService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.redeleitura.entity.Amizade;
import com.redeleitura.entity.Usuario;
import com.redeleitura.enums.StatusAmizade;
import com.redeleitura.repository.AmizadeRepository;
import com.redeleitura.repository.UsuarioRepository;

@Service
@Transactional
public class AmizadeServiceImpl implements AmizadeService {

    @Autowired
    private AmizadeRepository amizadeRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LivrosLidosRepository livrosLidosRepository;
    @Autowired
    private LivrosEmComumService livrosEmComumService;
    @Autowired
    private UsuarioLivrosEmComumMapper usuarioLivrosEmComumMapper;

    @Override
    public ResponseEntity<?> enviarSolicitacao(Integer idSolicitante, Integer idSolicitado) {
        Usuario solicitante = usuarioRepository.findById(idSolicitante)
            .orElseThrow(() -> new RuntimeException("Solicitante não encontrado"));

        Usuario solicitado = usuarioRepository.findById(idSolicitado)
            .orElseThrow(() -> new RuntimeException("Solicitado não encontrado"));

        Optional<Amizade> existente = amizadeRepository.findRelacionamentoEntreUsuarios(
                solicitante, solicitado, Arrays.asList(StatusAmizade.values())
        );

        if (existente.isPresent()) {
            Amizade amizade = existente.get();

            //FAZER O UPDATE NO BANCO
            if (amizade.getStatus().equals(StatusAmizade.RECUSADA)) {
                amizade.setStatus(StatusAmizade.PENDENTE);

                if(!amizade.getSolicitante().equals(solicitante)) {
                    amizade.setSolicitante(solicitante);
                    amizade.setSolicitado(solicitado);
                }

                amizadeRepository.save(amizade);
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe uma solicitação ou amizade entre esses usuários.");
            }
        // FAZER O INSERT NO BANCO
        } else {
            Amizade amizade = new Amizade();
            amizade.setSolicitante(solicitante);
            amizade.setSolicitado(solicitado);
            amizade.setStatus(StatusAmizade.PENDENTE);

            amizadeRepository.save(amizade);
        }

        return ResponseEntity.ok("Solicitação de amizade enviada com sucesso.");
    }

    @Override
    public ResponseEntity<?> aceitarSolicitacao(Long idSolicitacao) {
        Amizade amizade = amizadeRepository.findById(idSolicitacao)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));

        if(amizade.getStatus() == StatusAmizade.PENDENTE) {
            amizade.setStatus(StatusAmizade.ACEITA);
            amizadeRepository.save(amizade);

            return ResponseEntity.ok("Solicitação de amizade aceita.");
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body("Os usuários já são amigos ou não há uma solicitação pendente.");
    }

    @Override
    public ResponseEntity<?> removerSolicitacao(Long idSolicitacao) {
        Amizade amizade = amizadeRepository.findById(idSolicitacao)
                .orElseThrow(() -> new RuntimeException("Solicitação ou amizade não encontrada"));

        if(!(amizade.getStatus() == StatusAmizade.RECUSADA)) {
            amizade.setStatus(StatusAmizade.RECUSADA);
            amizadeRepository.save(amizade);

            return ResponseEntity.ok("Solicitação ou amizade removida com sucesso.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não há amizade nem solicitação pendente entre os usuários.");
    }

    @Override
    public List<UsuarioLivrosEmComumDTO> listarAmigos(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        List<Amizade> amizades = amizadeRepository.findByStatusAndUsuario(StatusAmizade.ACEITA, usuario);

        List<Usuario> amigos = amizades.stream()
                .map(am -> am.getSolicitante().equals(usuario) ? am.getSolicitado() : am.getSolicitante())
                .toList();

        Map<Usuario, Long> usuarioComumLivrosMap = livrosEmComumService.calcularLivrosEmComum(usuario, amigos);

        return usuarioLivrosEmComumMapper.toDTOList(usuarioComumLivrosMap, false);
    }
}