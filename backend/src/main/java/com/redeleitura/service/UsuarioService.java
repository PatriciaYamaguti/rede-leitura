package com.redeleitura.service;

import com.redeleitura.dto.UsuarioDTO;
import com.redeleitura.entity.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    Usuario cadastrarUsuario(UsuarioDTO usuarioDTO);

    List<Usuario> listarUsuarios();

    Optional<Usuario> buscarUsuarioPorId(String id);

    Usuario atualizarUsuario(Long id, UsuarioDTO usuarioDTO);

    void deletarUsuario(Long id);
}
