package com.redeleitura.repository;

import com.redeleitura.entity.Acesso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AcessoRepository extends JpaRepository<Acesso, Long> {
    List<Acesso> findByUsuarioId(long id);

    Optional<Acesso> findByUsuarioIdAndSenha(Integer usuarioId, String senha);
}
