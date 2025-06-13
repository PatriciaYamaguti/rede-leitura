package com.redeleitura.service;

import com.redeleitura.dto.UsuarioLivrosEmComumDTO;
import com.redeleitura.dto.UsuarioDTO;
import com.redeleitura.entity.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    UsuarioDTO cadastrarUsuario(UsuarioDTO usuarioDTO);

    Optional<Usuario> buscarUsuarioPorId(String id);

    Usuario atualizarUsuario(Long id, UsuarioDTO usuarioDTO);

    public List<UsuarioLivrosEmComumDTO> listarUsuariosPorInteresses(Integer idUsuario);

    void deletarUsuario(Long id);
}
