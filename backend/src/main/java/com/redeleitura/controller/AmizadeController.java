package com.redeleitura.controller;

import com.redeleitura.dto.AmizadeLogDTO;
import com.redeleitura.dto.UsuarioLivrosEmComumDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> listarAmizades(@RequestParam Integer idUsuario) {
        try {
            List<UsuarioLivrosEmComumDTO> amigos = amizadeService.listarAmigos(idUsuario);
            return ResponseEntity.ok(amigos);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao listar amigos.");
        }
    }

    @GetMapping
    public ResponseEntity<?> listarAmizadeLog(@RequestParam Integer idUsuario) {
        try {
            List<AmizadeLogDTO> logs = amizadeService.listarAmizadeLog(idUsuario);
            return ResponseEntity.ok(logs);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao listar logs de amizade.");
        }
    }

    @GetMapping("/status")
    public ResponseEntity<?> buscarStatusAmizade(
            @RequestParam Integer idUsuario1,
            @RequestParam Integer idUsuario2) {
        return amizadeService.buscarStatusAmizade(idUsuario1, idUsuario2);
    }

    @DeleteMapping("/marcarLido")
    public ResponseEntity<?> marcarComoLido(@RequestParam Integer idUsuario1, @RequestParam Integer idUsuario2) {
        return ResponseEntity.ok(amizadeService.marcarComoLido(idUsuario1, idUsuario2));
    }
}