package com.redeleitura.service;

import java.util.List;

import com.redeleitura.dto.UsuarioDTO;

public interface AmizadeService {
    String enviarSolicitacao(Integer idSolicitante, Integer idSolicitado);
    String aceitarSolicitacao(Integer idSolicitacao);
    String removerSolicitacao(Integer idSolicitacao);
    List<UsuarioDTO> listarAmigos();
}

 
