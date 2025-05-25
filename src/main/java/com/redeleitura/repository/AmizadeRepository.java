package com.redeleitura.repository;

import com.redeleitura.entity.Amizade;
import com.redeleitura.entity.AmizadeId;
import com.redeleitura.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AmizadeRepository extends JpaRepository<Amizade, AmizadeId> {
    List<Amizade> findByUsuario(Usuario usuario);
}

