package com.redeleitura.service;

import com.redeleitura.dto.UsuarioDTO;

import java.util.List;

public interface AmizadeService {
    public String enviarSolicitacao(Long idSolicitante, Long idSolicitado);
    public String aceitarSolicitacao(Long idSolicitacao);
    public String removerSolicitacao(Long idSolicitacao);
    public List<UsuarioDTO> listarAmigos();

}
