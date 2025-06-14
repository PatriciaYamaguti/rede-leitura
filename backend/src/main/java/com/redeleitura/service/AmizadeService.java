package com.redeleitura.service;

import java.util.List;

import com.redeleitura.dto.UsuarioLivrosEmComumDTO;

public interface AmizadeService {
    String enviarSolicitacao(Integer idSolicitante, Integer idSolicitado);
    String aceitarSolicitacao(Long idSolicitacao);
    String removerSolicitacao(Long idSolicitacao);
    List<UsuarioLivrosEmComumDTO> listarAmigos(Integer idUsuario);
}

 
