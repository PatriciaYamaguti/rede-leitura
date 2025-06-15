package com.redeleitura.service;

import com.redeleitura.dto.UsuarioLivrosEmComumDTO;
import com.redeleitura.dto.UsuarioDTO;
import com.redeleitura.entity.Usuario;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    ResponseEntity<?> cadastrarUsuario(UsuarioDTO usuarioDTO);

    Optional<Usuario> buscarUsuarioPorId(Integer id);

    ResponseEntity<?> atualizarUsuario(Integer id, UsuarioDTO usuarioDTO);

    public List<UsuarioLivrosEmComumDTO> listarUsuariosPorInteresses(Integer idUsuario);

    void deletarUsuario(Long id);
}
