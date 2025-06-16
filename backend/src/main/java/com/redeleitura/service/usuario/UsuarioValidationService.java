package com.redeleitura.service.usuario;

import com.redeleitura.entity.Usuario;
import com.redeleitura.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioValidationService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Optional<Usuario> validarExistenciaUsuarioPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> validarUsuarioPorNomeUsuario(String novoUsuario, Integer idAtual) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByUsuario(novoUsuario);

        if (usuarioExistente.isPresent() && !usuarioExistente.get().getId().equals(idAtual)) {
            return usuarioExistente;
        }

        return Optional.empty();
    }
}
