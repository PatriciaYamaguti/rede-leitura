package com.redeleitura.service.usuario;

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

    List<UsuarioLivrosEmComumDTO> listarUsuariosPorInteresses(Integer idUsuario);

    ResponseEntity<?> deletarUsuario(Integer id);

    ResponseEntity<?> logarUsuario(UsuarioDTO usuarioDTO);
}
