package com.redeleitura.service.livro;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.redeleitura.entity.LivroAtual;
import com.redeleitura.entity.LivrosLidos;
import com.redeleitura.entity.Usuario;
import com.redeleitura.repository.LivroAtualRepository;
import com.redeleitura.repository.LivrosLidosRepository;
import com.redeleitura.repository.UsuarioRepository;
import com.redeleitura.util.GoogleBooksUtil;

@Service
public class LivroServiceImpl implements LivroService {

    private final GoogleBooksUtil googleBooksUtil;
    private final UsuarioRepository usuarioRepository;
    private final LivrosLidosRepository livrosLidosRepository;
    private final LivroAtualRepository livroAtualRepository;

    public LivroServiceImpl(GoogleBooksUtil googleBooksUtil, UsuarioRepository usuarioRepository, LivrosLidosRepository livrosLidosRepository, LivroAtualRepository livroAtualRepository) {
        this.googleBooksUtil = googleBooksUtil;
        this.usuarioRepository = usuarioRepository;
        this.livrosLidosRepository = livrosLidosRepository;
        this.livroAtualRepository = livroAtualRepository;
    }

    @Override
    public LivrosLidos marcarLivroComoLido(Integer idUsuario, String isbn) {
        Usuario usuario = verificarUsuarioBanco(idUsuario);
        String isbnLimpo = isbn.trim();

        Optional<LivrosLidos> livroLidoExistente = livrosLidosRepository.findByUsuarioAndIsbn(usuario, isbnLimpo);

        if (livroLidoExistente.isPresent()) {
            // Se o livro já está marcado como lido e é o livro atual, limpa o livro atual
            if (usuario.getLivroAtual() != null && isbnLimpo.equals(usuario.getLivroAtual().getIsbn())) {
                usuario.setLivroAtual(null);
                usuarioRepository.save(usuario);
            }
            // Retorna o livro lido existente (sem inserir nada novo)
            return livroLidoExistente.get();
        }

        // Caso não tenha sido marcado ainda, marca o livro como lido normalmente
        GoogleBooksUtil.LivroDTO livroDTO = buscarLivroPorIsbn(isbnLimpo);

        LivrosLidos livroLido = new LivrosLidos();
        livroLido.setUsuario(usuario);
        livroLido.setIsbn(livroDTO.isbn().trim());
        livroLido.setTitulo(livroDTO.titulo());
        livroLido.setAutor(livroDTO.autor());

        LivrosLidos salvo = livrosLidosRepository.save(livroLido);

        // Se o livro era o livro atual, limpa o livro atual do usuário
        if (usuario.getLivroAtual() != null && isbnLimpo.equals(usuario.getLivroAtual().getIsbn())) {
            usuario.setLivroAtual(null);
            usuarioRepository.save(usuario);
        }

        return salvo;
    }

    @Override
    public LivroAtual definirLivroAtual(Integer idUsuario, String isbn) {
        // Verifica se o usuário existe no banco, lança exceção se não existir
        Usuario usuario = verificarUsuarioBanco(idUsuario);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário com ID " + idUsuario + " não encontrado.");
        }

        // Remove espaços extras do ISBN
        String isbnLimpo = isbn.trim();

        // Busca o livro pelo ISBN
        GoogleBooksUtil.LivroDTO livroDTO = buscarLivroPorIsbn(isbnLimpo);
        if (livroDTO == null) {
            throw new IllegalArgumentException("Livro com ISBN " + isbnLimpo + " não encontrado.");
        }

        // Busca LivroAtual do usuário no banco
        LivroAtual livroAtual = livroAtualRepository.findByUsuarioId(idUsuario);

        if (livroAtual != null) {
            // Atualiza os dados do livro atual existente
            livroAtual.setAutor(livroDTO.autor());
            livroAtual.setIsbn(livroDTO.isbn().trim());
            livroAtual.setTitulo(livroDTO.titulo());
            livroAtualRepository.save(livroAtual);
            return livroAtual;
        } else {
            // Cria um novo LivroAtual
            LivroAtual novoLivro = criarLivroAtual(livroDTO, usuario);
            livroAtualRepository.save(novoLivro);
            return novoLivro;
        }
    }

    private Usuario verificarUsuarioBanco(Integer idUsuario) {
        return usuarioRepository.findByIdAndDataExpiracaoIsNull(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não encontrado"));
    }

    private GoogleBooksUtil.LivroDTO buscarLivroPorIsbn(String isbn) {
        return googleBooksUtil.buscarLivroPorIsbn(isbn.trim());
    }

    private LivroAtual criarLivroAtual(GoogleBooksUtil.LivroDTO livroDTO, Usuario usuario) {
        LivroAtual livroAtual = new LivroAtual();
        livroAtual.setIsbn(livroDTO.isbn().trim());
        livroAtual.setTitulo(livroDTO.titulo());
        livroAtual.setAutor(livroDTO.autor());
        livroAtual.setUsuario(usuario);
        return livroAtual;
    }
}
