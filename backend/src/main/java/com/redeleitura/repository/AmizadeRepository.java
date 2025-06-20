package com.redeleitura.repository;

import com.redeleitura.entity.Amizade;
import com.redeleitura.entity.AmizadeLog;
import com.redeleitura.entity.Usuario;
import com.redeleitura.enums.StatusAmizade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface AmizadeRepository extends JpaRepository<Amizade, Long> {

    @Query("SELECT s FROM Amizade s WHERE " +
            "((s.solicitante = :u1 AND s.solicitado = :u2) OR (s.solicitante = :u2 AND s.solicitado = :u1)) " +
            "AND s.status IN (:status)")
    Optional<Amizade> findRelacionamentoEntreUsuarios(
            @Param("u1") Usuario u1,
            @Param("u2") Usuario u2,
            @Param("status") List<StatusAmizade> status);

    @Query("SELECT a FROM Amizade a WHERE (a.solicitante = :usuario OR a.solicitado = :usuario) AND a.status = :status")
    List<Amizade> findByStatusAndUsuario(@Param("status") StatusAmizade status, @Param("usuario") Usuario usuario);

    @Modifying
    @Query("DELETE FROM Amizade a WHERE a.solicitante.id = :userId OR a.solicitado.id = :userId")
    void deleteByUsuarioId(@Param("userId") Integer userId);

    @Query("SELECT a FROM Amizade a WHERE a.solicitante = :usuario OR a.solicitado = :usuario")
    List<Amizade> findAllByUsuario(@Param("usuario") Usuario usuario);

    @Query("SELECT a FROM Amizade a WHERE (a.solicitante = :usuario1 AND a.solicitado = :usuario2) OR (a.solicitante = :usuario2 AND a.solicitado = :usuario1)")
    Optional<Amizade> findByUsuarios(@Param("usuario1") Usuario usuario1, @Param("usuario2") Usuario usuario2);
}

