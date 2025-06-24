package com.redeleitura.repository;

import com.redeleitura.entity.Amizade;
import com.redeleitura.entity.AmizadeLog;
import com.redeleitura.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AmizadeLogRepository extends JpaRepository<AmizadeLog, Long> {

    List<AmizadeLog> findByAmizadeAndAtivaTrueOrderByDataHoraDesc(Amizade amizade);
    List<AmizadeLog> findByAmizadeAndUsuarioIdAndAtivaTrueOrderByDataHoraDesc(Amizade amizade, Integer idUsuario);
    Optional<AmizadeLog> findFirstByAmizadeAndAtivaTrueOrderByDataHoraDesc(Amizade amizade);
    List<AmizadeLog> findByUsuarioAndAtivaTrue(Usuario usuario);
}