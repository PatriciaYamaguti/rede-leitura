package com.redeleitura.repository;

import com.redeleitura.entity.SolicitacaoAmizade;
import com.redeleitura.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SolicitacaoAmizadeRepository extends JpaRepository<SolicitacaoAmizade, Integer> {
    List<SolicitacaoAmizade> findBySolicitanteOrSolicitado(Usuario solicitante, Usuario solicitado);
}

