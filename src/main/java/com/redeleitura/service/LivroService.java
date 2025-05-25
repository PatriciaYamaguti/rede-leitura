package com.redeleitura.service;

import java.util.Optional;

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

        // Limpar o ISBN recebido da requisição
        String isbnLimpo = isbn.trim();

        // Verificar se o livro já foi lido por esse usuário
        Optional<LivrosLidos> livroExistente = livrosLidosRepository.findByUsuarioAndIsbn(usuario, isbnLimpo);
        if (livroExistente.isPresent()) {
        throw new RuntimeException("Este livro já foi marcado como lido por este usuário.");
        }

        // Buscar dados do livro na API
        GoogleBooksService.LivroDTO livroDTO = googleBooksService.buscarLivroPorIsbn(isbnLimpo);

        // Criar o objeto livro lido
        LivrosLidos livroLido = new LivrosLidos();
        livroLido.setUsuario(usuario);
        livroLido.setIsbn(livroDTO.isbn().trim());  // limpar também o retorno da API
        livroLido.setTitulo(livroDTO.titulo());
        livroLido.setAutor(livroDTO.autor());

        // Salvar no banco
        LivrosLidos salvo = livrosLidosRepository.save(livroLido);

        // Se o livro lido for o mesmo do livro atual, limpar o campo
        if (isbnLimpo.equals(usuario.getLivroAtualIsbn())) {
            usuario.setLivroAtualIsbn(null);
            usuarioRepository.save(usuario);
    }

        return salvo;
    }


    public Usuario definirLivroAtual(Integer idUsuario, String isbn) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

    // Remover espaços e quebras de linha no ISBN
        String isbnLimpo = isbn.trim();
        usuario.setLivroAtualIsbn(isbnLimpo);

        return usuarioRepository.save(usuario);
    }

}

