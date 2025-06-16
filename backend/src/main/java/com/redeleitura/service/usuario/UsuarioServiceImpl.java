package com.redeleitura.service.usuario;

import com.redeleitura.dto.UsuarioLivrosEmComumDTO;
import com.redeleitura.dto.UsuarioDTO;
import com.redeleitura.entity.Acesso;
import com.redeleitura.entity.Usuario;
import com.redeleitura.mapper.UsuarioLivrosEmComumMapper;
import com.redeleitura.mapper.UsuarioMapper;
import com.redeleitura.repository.AcessoRepository;
import com.redeleitura.repository.AmizadeRepository;
import com.redeleitura.repository.UsuarioRepository;
import com.redeleitura.service.livro.LivrosEmComumService;
import com.redeleitura.util.HashUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AcessoRepository acessoRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private LivrosEmComumService livrosEmComumService;
    @Autowired
    private UsuarioLivrosEmComumMapper usuarioLivrosEmComumMapper;
    @Autowired
    private AmizadeRepository amizadeRepository;

    @Override
    public ResponseEntity<?> cadastrarUsuario(UsuarioDTO usuarioDTO) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByUsuario(usuarioDTO.getUsuario());

        if (usuarioExistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe um usuário com o mesmo nome de usuário.");
        }

        String senhaHash = HashUtil.gerarHashSHA256(usuarioDTO.getAcesso().getSenha());

        Usuario usuario = usuarioMapper.toUsuarioEntity(usuarioDTO);
        Acesso acesso = new Acesso(usuario, "USER", senhaHash);
        usuario.setAcesso(acesso);
        usuario.setDataCadastro(LocalDateTime.now());

        usuarioRepository.save(usuario);
        acessoRepository.save(acesso);
        return ResponseEntity.ok("Cadastro realizado com sucesso.");
    }

    @Override
    public List<UsuarioLivrosEmComumDTO> listarUsuariosPorInteresses(Integer idUsuario) {
        Usuario usuarioAtual = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        List<Usuario> outrosUsuarios = usuarioRepository.findAll().stream()
                .filter(u -> !u.getId().equals(usuarioAtual.getId()))
                .toList();

        Map<Usuario, Long> usuarioComumLivrosMap = livrosEmComumService.calcularLivrosEmComum(usuarioAtual, outrosUsuarios);

        return usuarioLivrosEmComumMapper.toDTOList(usuarioComumLivrosMap, true);
    }

    @Override
    public Optional<Usuario> buscarUsuarioPorId(Integer id) {
        return Optional.empty();
    }

    @Override
    public ResponseEntity<?> atualizarUsuario(Integer id, UsuarioDTO usuarioDTO) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }

        Optional<Usuario> usuarioExistente = usuarioRepository.findByUsuario(usuarioDTO.getUsuario());
        if (usuarioExistente.isPresent() && !usuarioExistente.get().getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe um usuário com o mesmo nome de usuário.");
        }

        Usuario usuario = usuarioOptional.get();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setDescricao(usuarioDTO.getDescricao());
        usuario.setUsuario(usuarioDTO.getUsuario());

        usuarioRepository.save(usuario);

        return ResponseEntity.ok("Informações de usuário atualizadas com sucesso.");
    }

    @Override
    public ResponseEntity<?> deletarUsuario(Integer id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }

        Usuario usuario = usuarioOptional.get();
        usuario.setDataExpiracao(LocalDateTime.now());
        amizadeRepository.deleteByUsuarioId(usuario.getId());

        usuarioRepository.save(usuario);

        return ResponseEntity.ok("Usuário deletado com sucesso.");
    }

    @Override
    public ResponseEntity<?> logarUsuario(UsuarioDTO usuarioDTO) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByUsuario(usuarioDTO.getUsuario());
        if (usuarioExistente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Nome de usuário não existente.");
        }

        Usuario usuario = usuarioExistente.get();
        Optional<Acesso> acessoOptional = acessoRepository.findByUsuarioIdAndSenha(usuario.getId(), HashUtil.gerarHashSHA256(usuarioDTO.getAcesso().getSenha()));
        if (acessoOptional.isPresent()) {
            return ResponseEntity.ok("Usuário logado com sucesso.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Senha incorreta.");
    }
}