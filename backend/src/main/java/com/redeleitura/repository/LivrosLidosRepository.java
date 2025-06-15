package com.redeleitura.repository;

import com.redeleitura.entity.LivrosLidos;
import com.redeleitura.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LivrosLidosRepository extends JpaRepository<LivrosLidos, Long> {
    List<LivrosLidos> findByUsuario(Usuario usuario);
    Optional<LivrosLidos> findByUsuarioAndIsbn(Usuario usuario, String isbn);

}
