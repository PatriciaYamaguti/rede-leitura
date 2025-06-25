package com.redeleitura.service.usuario;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.redeleitura.entity.*;
import com.redeleitura.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.redeleitura.dto.UsuarioDTO;
import com.redeleitura.dto.UsuarioLivrosDTO;
import com.redeleitura.dto.UsuarioLivrosEmComumDTO;
import com.redeleitura.mapper.LivroMapper;
import com.redeleitura.mapper.UsuarioLivrosEmComumMapper;
import com.redeleitura.mapper.UsuarioLivrosMapper;
import com.redeleitura.mapper.UsuarioMapper;
import com.redeleitura.service.livro.LivrosEmComumService;
import com.redeleitura.util.HashUtil;

import jakarta.transaction.Transactional;

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
    @Autowired
    private LivrosLidosRepository livrosLidosRepository;

    @Autowired
    private UsuarioLivrosMapper usuarioLivrosMapper;
    @Autowired
    private LivroMapper livroMapper;
    @Autowired
    private AmizadeLogRepository amizadeLogRepository;

    @Override
    public ResponseEntity<?> cadastrarUsuario(UsuarioDTO usuarioDTO) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByUsuario(usuarioDTO.getUsuario());

        if (usuarioExistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe um usuário com o mesmo nome de usuário.");
        }

        if(!validarUsuario(usuarioDTO)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Informações do usuário não correspondem as validações corretas.");
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

    private boolean validarUsuario(UsuarioDTO usuarioDTO) {
        return usuarioDTO.getUsuario().length() <= 20
                && usuarioDTO.getNome().length() <= 80
                && usuarioDTO.getAcesso().getSenha().length() >= 3
                && usuarioDTO.getDescricao().length() >= 10;
    }

    @Override
    public List<UsuarioLivrosEmComumDTO> listarUsuariosPorInteresses(Integer idUsuario) {
        Usuario usuarioAtual = usuarioRepository.findByIdAndDataExpiracaoIsNull(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        List<Usuario> outrosUsuarios = usuarioRepository.findAllByDataExpiracaoIsNull().stream()
                .filter(u -> !u.getId().equals(usuarioAtual.getId()))
                .toList();

        Map<Usuario, Long> usuarioComumLivrosMap = livrosEmComumService.calcularLivrosEmComum(usuarioAtual, outrosUsuarios);

        return usuarioLivrosEmComumMapper.toDTOList(usuarioComumLivrosMap, true);
    }

    @Override
    public ResponseEntity<?> buscarUsuarioPorId(Integer id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByIdAndDataExpiracaoIsNull(id);
        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado com o ID: " + id);
        }
        Usuario usuario = usuarioOptional.get();
        UsuarioLivrosDTO usuariolivrosDTO = usuarioLivrosMapper.toUsuarioLivrosDTO(usuario);

        List<LivrosLidos> livrosLidos = livrosLidosRepository.findByUsuario(usuario);
        if (!livrosLidos.isEmpty()) {
            usuariolivrosDTO.setLivrosLidos(livroMapper.toLivrosLidosDTO(livrosLidos));

            usuariolivrosDTO.getLivrosLidos().forEach(livro -> livro.setUsuarioDTO(null));
        }

        return ResponseEntity.ok(usuariolivrosDTO);
    }

    @Override
    public ResponseEntity<?> atualizarUsuario(Integer id, UsuarioDTO usuarioDTO) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByIdAndDataExpiracaoIsNull(id);
        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }

        if(!(usuarioDTO.getUsuario().length() <= 20 && usuarioDTO.getNome().length() <= 80 && usuarioDTO.getDescricao().length() >= 10)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Informações do usuário não correspondem as validações corretas.");
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
        Optional<Usuario> usuarioOptional = usuarioRepository.findByIdAndDataExpiracaoIsNull(id);
        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }

        Usuario usuario = usuarioOptional.get();
        usuario.setDataExpiracao(LocalDateTime.now());

        List<Amizade> amizades = amizadeRepository.findAllByUsuario(usuario);
        for (Amizade amizade : amizades) {
            amizade.setExcluida(true);
            amizadeRepository.save(amizade);
        }

        List<AmizadeLog> logs = amizadeLogRepository.findByUsuarioAndAtivaTrue(usuario);
        for (AmizadeLog log : logs) {
            log.setAtiva(false);
        }
        amizadeLogRepository.saveAll(logs);

        usuarioRepository.save(usuario);

        return ResponseEntity.ok("Usuário deletado com sucesso.");
    }

    @Override
    public ResponseEntity<?> logarUsuario(UsuarioDTO usuarioDTO) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByUsuarioAndDataExpiracaoIsNull(usuarioDTO.getUsuario());
        if (usuarioExistente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Nome de usuário não existente.");
        }

        Usuario usuario = usuarioExistente.get();
        Optional<Acesso> acessoOptional = acessoRepository.findByUsuarioIdAndSenha(
                usuario.getId(),
                HashUtil.gerarHashSHA256(usuarioDTO.getAcesso().getSenha())
        );

        if (acessoOptional.isPresent()) {
            Map<String, Object> resposta = new HashMap<>();
            resposta.put("mensagem", "Usuário logado com sucesso.");
            resposta.put("idUsuario", usuario.getId());

            return ResponseEntity.ok(resposta);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Senha incorreta.");
    }
}