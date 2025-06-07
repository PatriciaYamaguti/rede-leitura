package com.redeleitura.repository;

import com.redeleitura.entity.LivroAtual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroAtualRepository extends JpaRepository<LivroAtual, Long> {
    LivroAtual findByUsuarioId(Integer usuarioId);

}
