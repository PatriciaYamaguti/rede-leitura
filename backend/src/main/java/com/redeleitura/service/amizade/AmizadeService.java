package com.redeleitura.service.amizade;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.redeleitura.dto.AmizadeLogDTO;
import com.redeleitura.dto.UsuarioLivrosEmComumDTO;

public interface AmizadeService {
    ResponseEntity<?> enviarSolicitacao(Integer idSolicitante, Integer idSolicitado);
    ResponseEntity<?> aceitarSolicitacao(Long idSolicitacao);
    ResponseEntity<?> removerSolicitacao(Long idSolicitacao);
    List<UsuarioLivrosEmComumDTO> listarAmigos(Integer idUsuario);
    List<AmizadeLogDTO> listarAmizadeLog(Integer idUsuario);
    ResponseEntity<?> buscarStatusAmizade(Integer idUsuario1, Integer idUsuario2);
    ResponseEntity<?> marcarComoLido(Integer idUsuario1, Integer idUsuario2);
}

 
