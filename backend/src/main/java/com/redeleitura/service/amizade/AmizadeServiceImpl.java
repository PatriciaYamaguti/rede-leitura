package com.redeleitura.service.amizade;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.redeleitura.dto.AmizadeLogDTO;
import com.redeleitura.dto.StatusAmizadeDTO;
import com.redeleitura.dto.UsuarioLivrosEmComumDTO;
import com.redeleitura.entity.Amizade;
import com.redeleitura.entity.AmizadeLog;
import com.redeleitura.entity.Usuario;
import com.redeleitura.enums.StatusAmizade;
import com.redeleitura.mapper.AmizadeLogMapper;
import com.redeleitura.mapper.UsuarioLivrosEmComumMapper;
import com.redeleitura.repository.AmizadeLogRepository;
import com.redeleitura.repository.AmizadeRepository;
import com.redeleitura.repository.UsuarioRepository;
import com.redeleitura.service.livro.LivrosEmComumService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AmizadeServiceImpl implements AmizadeService {

    @Autowired
    private AmizadeRepository amizadeRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LivrosEmComumService livrosEmComumService;

    @Autowired
    private UsuarioLivrosEmComumMapper usuarioLivrosEmComumMapper;

    @Autowired
    private AmizadeLogMapper amizadeLogMapper;

    @Autowired
    private AmizadeLogRepository amizadeLogRepository;

    @Override
    public ResponseEntity<?> enviarSolicitacao(Integer idSolicitante, Integer idSolicitado) {
        Usuario solicitante = usuarioRepository.findByIdAndDataExpiracaoIsNull(idSolicitante)
            .orElseThrow(() -> new RuntimeException("Solicitante não encontrado"));

        Usuario solicitado = usuarioRepository.findByIdAndDataExpiracaoIsNull(idSolicitado)
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
                registrarLogAmizade(amizade, solicitado, "Solicitação recebida.");
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
            registrarLogAmizade(amizade, solicitado, "Solicitação recebida.");
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

            registrarLogAmizade(amizade, amizade.getSolicitante(), "Solicitação aceita.");

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

            limparLogAmizade(amizade);

            return ResponseEntity.ok("Solicitação ou amizade removida com sucesso.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não há amizade nem solicitação pendente entre os usuários.");
    }

    @Override
    public List<UsuarioLivrosEmComumDTO> listarAmigos(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findByIdAndDataExpiracaoIsNull(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        List<Amizade> amizades = amizadeRepository.findByStatusAndUsuario(StatusAmizade.ACEITA, usuario);

        List<Usuario> amigos = amizades.stream()
                .map(am -> am.getSolicitante().equals(usuario) ? am.getSolicitado() : am.getSolicitante())
                .toList();

        Map<Usuario, Long> usuarioComumLivrosMap = livrosEmComumService.calcularLivrosEmComum(usuario, amigos);

        return usuarioLivrosEmComumMapper.toDTOList(usuarioComumLivrosMap, false);
    }

    @Override
    public ResponseEntity<?> buscarStatusAmizade(Integer idUsuario1, Integer idUsuario2) {
        Usuario usuario1 = usuarioRepository.findByIdAndDataExpiracaoIsNull(idUsuario1)
                .orElseThrow(() -> new RuntimeException("Usuário 1 não encontrado"));

        Usuario usuario2 = usuarioRepository.findByIdAndDataExpiracaoIsNull(idUsuario2)
                .orElseThrow(() -> new RuntimeException("Usuário 2 não encontrado"));

        Optional<Amizade> amizadeOptional = amizadeRepository.findByUsuarios(usuario1, usuario2);

        if (amizadeOptional.isPresent()) {
            Amizade amizade = amizadeOptional.get();
            String status = amizade.getStatus().name();
            Integer idSolicitante = amizade.getSolicitante().getId();

            StatusAmizadeDTO statusDTO = new StatusAmizadeDTO(true, status, amizade.getId(), idSolicitante);
            return ResponseEntity.ok(statusDTO);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Não há amizade nem solicitação pendente entre os usuários.");
    }

    @Override
    public List<AmizadeLogDTO> listarAmizadeLog(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findByIdAndDataExpiracaoIsNull(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Buscar todas as amizades onde o usuário está envolvido
        List<Amizade> amizades = amizadeRepository.findAllByUsuario(usuario);

        List<AmizadeLogDTO> resultado = new ArrayList<>();

        for (Amizade amizade : amizades) {
            // Buscar apenas o log ativo daquela amizade
            List<AmizadeLog> logsAtivos = amizadeLogRepository.findByAmizadeAndUsuarioIdAndAtivaTrueOrderByDataHoraDesc(amizade, idUsuario);

            for (AmizadeLog log : logsAtivos) {
                Usuario outroUsuario = amizade.getSolicitante().getId().equals(idUsuario)
                        ? amizade.getSolicitado()
                        : amizade.getSolicitante();

                AmizadeLogDTO dto = amizadeLogMapper.toDTO(log, outroUsuario);
                resultado.add(dto);
            }
        }

        return resultado;
    }

    public ResponseEntity<?> marcarComoLido(Integer idUsuario1, Integer idUsuario2) {
        Usuario usuario1 = usuarioRepository.findById(idUsuario1)
                .orElseThrow(() -> new RuntimeException("Usuário 1 não encontrado"));

        Usuario usuario2 = usuarioRepository.findById(idUsuario2)
                .orElseThrow(() -> new RuntimeException("Usuário 2 não encontrado"));

        Optional<Amizade> amizadeOptional = amizadeRepository.findByUsuarios(usuario1, usuario2);

        if (amizadeOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não há amizade nem solicitação pendente entre os usuários.");
        }

        Amizade amizade = amizadeOptional.get();
        limparLogAmizade(amizade);

        return ResponseEntity.ok().build();
    }

    @Transactional
    public void registrarLogAmizade(Amizade amizade, Usuario usuarioAcao, String descricao) {
        limparLogAmizade(amizade);

        AmizadeLog novoLog = new AmizadeLog();
        novoLog.setAmizade(amizade);
        novoLog.setUsuario(usuarioAcao);
        novoLog.setDescricao(descricao);
        novoLog.setStatus(amizade.getStatus());
        novoLog.setAtiva(true);
        novoLog.setDataHora(LocalDateTime.now());

        amizadeLogRepository.save(novoLog);
    }

    @Transactional
    public void limparLogAmizade(Amizade amizade) {
        List<AmizadeLog> logsAtivos = amizadeLogRepository.findByAmizadeAndAtivaTrueOrderByDataHoraDesc(amizade);

        for (AmizadeLog log : logsAtivos) {
            log.setAtiva(false);
        }
        amizadeLogRepository.saveAll(logsAtivos);
    }
}