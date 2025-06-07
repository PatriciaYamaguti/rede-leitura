package com.redeleitura.service;

import com.redeleitura.entity.LivroAtual;
import com.redeleitura.entity.LivrosLidos;
import com.redeleitura.entity.Usuario;
import com.redeleitura.repository.LivrosLidosRepository;
import com.redeleitura.repository.UsuarioRepository;
import com.redeleitura.util.GoogleBooksUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class LivroService {

    private final GoogleBooksUtil googleBooksUtil;
    private final UsuarioRepository usuarioRepository;
    private final LivrosLidosRepository livrosLidosRepository;

    public LivroService(GoogleBooksUtil googleBooksUtil, UsuarioRepository usuarioRepository, LivrosLidosRepository livrosLidosRepository) {
        this.googleBooksUtil = googleBooksUtil;
        this.usuarioRepository = usuarioRepository;
        this.livrosLidosRepository = livrosLidosRepository;
    }

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

    public LivroAtual definirLivroAtual(Integer idUsuario, String isbn) {
        Usuario usuario = verificarUsuarioBanco(idUsuario);
        String isbnLimpo = isbn.trim();

        GoogleBooksUtil.LivroDTO livroDTO = buscarLivroPorIsbn(isbnLimpo);

        if (usuario.getLivroAtual() != null) {
            usuario.setLivroAtual(null);
        }

        LivroAtual novoLivroAtual = new LivroAtual();
        novoLivroAtual.setIsbn(livroDTO.isbn().trim());
        novoLivroAtual.setTitulo(livroDTO.titulo());
        novoLivroAtual.setAutor(livroDTO.autor());
        novoLivroAtual.setUsuario(usuario);

        usuario.setLivroAtual(novoLivroAtual);

        usuarioRepository.save(usuario);

        return novoLivroAtual;
    }

    private Usuario verificarUsuarioBanco(Integer idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não encontrado"));
    }

    private GoogleBooksUtil.LivroDTO buscarLivroPorIsbn(String isbn) {
        return googleBooksUtil.buscarLivroPorIsbn(isbn.trim());
    }
}
