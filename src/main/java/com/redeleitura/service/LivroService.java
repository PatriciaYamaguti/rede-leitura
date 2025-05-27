package com.redeleitura.service;

import java.util.Optional;

import com.redeleitura.entity.LivroAtual;
import org.springframework.stereotype.Service;

import com.redeleitura.entity.LivrosLidos;
import com.redeleitura.entity.Usuario;
import com.redeleitura.repository.LivrosLidosRepository;
import com.redeleitura.repository.UsuarioRepository;

@Service
public class LivroService {

    private final GoogleBooksService googleBooksService;
    private final UsuarioRepository usuarioRepository;
    private final LivrosLidosRepository livrosLidosRepository;

    public LivroService(GoogleBooksService googleBooksService, UsuarioRepository usuarioRepository, LivrosLidosRepository livrosLidosRepository) {
        this.googleBooksService = googleBooksService;
        this.usuarioRepository = usuarioRepository;
        this.livrosLidosRepository = livrosLidosRepository;
    }

    public LivrosLidos marcarLivroComoLido(Integer idUsuario, String isbn) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String isbnLimpo = isbn.trim();

        // Verificar se já foi lido
        Optional<LivrosLidos> livroExistente = livrosLidosRepository.findByUsuarioAndIsbn(usuario, isbnLimpo);
        if (livroExistente.isPresent()) {
            throw new RuntimeException("Este livro já foi marcado como lido por este usuário.");
        }

        // Buscar na API
        GoogleBooksService.LivroDTO livroDTO = googleBooksService.buscarLivroPorIsbn(isbnLimpo);

        LivrosLidos livroLido = new LivrosLidos();
        livroLido.setUsuario(usuario);
        livroLido.setIsbn(livroDTO.isbn().trim());
        livroLido.setTitulo(livroDTO.titulo());
        livroLido.setAutor(livroDTO.autor());

        LivrosLidos salvo = livrosLidosRepository.save(livroLido);

        // Limpar livroAtual se coincidir
        if (usuario.getLivroAtual() != null && isbnLimpo.equals(usuario.getLivroAtual().getIsbn())) {
            usuario.setLivroAtual(null);
            usuarioRepository.save(usuario);
        }

        return salvo;
    }

    public Usuario definirLivroAtual(Integer idUsuario, String isbn) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String isbnLimpo = isbn.trim();

        // Buscar dados do livro
        GoogleBooksService.LivroDTO livroDTO = googleBooksService.buscarLivroPorIsbn(isbnLimpo);

        // Criar entidade LivroAtual
        LivroAtual livroAtual = new LivroAtual();
        livroAtual.setIsbn(livroDTO.isbn().trim());
        livroAtual.setTitulo(livroDTO.titulo());
        livroAtual.setAutor(livroDTO.autor());
        livroAtual.setUsuario(usuario);

        // Atualizar o livro atual do usuário
        usuario.setLivroAtual(livroAtual);

        return usuarioRepository.save(usuario); // Cascade.ALL deve persistir o LivroAtual automaticamente
    }

}

