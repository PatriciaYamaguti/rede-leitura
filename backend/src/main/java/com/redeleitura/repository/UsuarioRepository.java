package com.redeleitura.repository;

import com.redeleitura.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByUsuario(String usuario);
    Optional<Usuario> findByUsuarioAndDataExpiracaoIsNull(String usuario);
    Optional<Usuario> findByIdAndDataExpiracaoIsNull(Integer id);
    List<Usuario> findAllByDataExpiracaoIsNull();



}

