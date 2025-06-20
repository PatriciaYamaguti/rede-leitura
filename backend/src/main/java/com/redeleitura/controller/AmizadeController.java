package com.redeleitura.controller;

import com.redeleitura.dto.AmizadeLogDTO;
import com.redeleitura.dto.StatusAmizadeDTO;
import com.redeleitura.dto.UsuarioLivrosEmComumDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.redeleitura.service.amizade.AmizadeService;

import java.util.List;

@RestController
@RequestMapping("/api/amizade")
public class AmizadeController {

    @Autowired
    private AmizadeService amizadeService;

    @PostMapping("/solicitar")
    public ResponseEntity<?> enviarSolicitacao(
            @RequestParam Integer idSolicitante,
            @RequestParam Integer idSolicitado) {
        return amizadeService.enviarSolicitacao(idSolicitante, idSolicitado);
    }

    @PostMapping("/aceitar")
    public ResponseEntity<?> aceitarSolicitacao(@RequestParam Long idSolicitacao) {
        return amizadeService.aceitarSolicitacao(idSolicitacao);
    }

    @PostMapping("/recusar")
    public ResponseEntity<?> removerSolicitacao(@RequestParam Long idSolicitacao) {
        return amizadeService.removerSolicitacao(idSolicitacao);
    }

    @PostMapping("/listar")
    public List<UsuarioLivrosEmComumDTO> listarAmizades(@RequestParam Integer idUsuario) {
        return amizadeService.listarAmigos(idUsuario);
    }

    @GetMapping()
    public List<AmizadeLogDTO> listarAmizadeLog(Integer idUsuario) {
        return amizadeService.listarAmizadeLog(idUsuario);
    }

    @GetMapping("/status")
    public ResponseEntity<StatusAmizadeDTO> buscarStatusAmizade(
            @RequestParam Integer idUsuario1,
            @RequestParam Integer idUsuario2) {
        StatusAmizadeDTO status = amizadeService.buscarStatusAmizade(idUsuario1, idUsuario2);
        return ResponseEntity.ok(status);
    }
}



