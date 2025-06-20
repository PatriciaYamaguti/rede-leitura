package com.redeleitura.service.amizade;

import java.util.List;

import com.redeleitura.dto.AmizadeLogDTO;
import com.redeleitura.dto.StatusAmizadeDTO;
import com.redeleitura.dto.UsuarioLivrosEmComumDTO;
import org.springframework.http.ResponseEntity;

public interface AmizadeService {
    ResponseEntity<?> enviarSolicitacao(Integer idSolicitante, Integer idSolicitado);
    ResponseEntity<?> aceitarSolicitacao(Long idSolicitacao);
    ResponseEntity<?> removerSolicitacao(Long idSolicitacao);
    List<UsuarioLivrosEmComumDTO> listarAmigos(Integer idUsuario);
    List<AmizadeLogDTO> listarAmizadeLog(Integer idUsuario);
    StatusAmizadeDTO buscarStatusAmizade(Integer idUsuario1, Integer idUsuario2);
}

 
