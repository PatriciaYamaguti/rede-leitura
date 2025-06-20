package com.redeleitura.repository;

import com.redeleitura.entity.Amizade;
import com.redeleitura.entity.AmizadeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AmizadeLogRepository extends JpaRepository<AmizadeLog, Long> {

    List<AmizadeLog> findByAmizadeAndAtivaTrueOrderByDataHoraDesc(Amizade amizade);
    List<AmizadeLog> findByAmizadeAndUsuarioIdAndAtivaTrueOrderByDataHoraDesc(Amizade amizade, Integer idUsuario);

}