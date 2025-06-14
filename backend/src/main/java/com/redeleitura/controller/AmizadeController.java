package com.redeleitura.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.redeleitura.service.AmizadeService;

@RestController
@RequestMapping("/api/amizade")
public class AmizadeController {

    @Autowired
    private AmizadeService amizadeService;

    @PostMapping("/solicitar")
    public String enviarSolicitacao(
            @RequestParam Integer idSolicitante,
            @RequestParam Integer idSolicitado) {
        return amizadeService.enviarSolicitacao(idSolicitante, idSolicitado);
    }

    @PostMapping("/aceitar")
    public String aceitarSolicitacao(@RequestParam Long idSolicitacao) {
        return amizadeService.aceitarSolicitacao(idSolicitacao);
    }

    @PostMapping("/recusar")
    public String removerSolicitacao(@RequestParam Long idSolicitacao) {
        return amizadeService.removerSolicitacao(idSolicitacao);
    }
    
}



