package com.redeleitura.service.usuario;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.redeleitura.dto.UsuarioDTO;
import com.redeleitura.dto.UsuarioLivrosEmComumDTO;

public interface UsuarioService {
    ResponseEntity<?> cadastrarUsuario(UsuarioDTO usuarioDTO);

    ResponseEntity<?> buscarUsuarioPorId(Integer id);

    ResponseEntity<?> atualizarUsuario(Integer id, UsuarioDTO usuarioDTO);

    List<UsuarioLivrosEmComumDTO> listarUsuariosPorInteresses(Integer idUsuario);

    ResponseEntity<?> deletarUsuario(Integer id);

    ResponseEntity<?> logarUsuario(UsuarioDTO usuarioDTO);
}
