package com.redeleitura.service;

import com.redeleitura.entity.LivroAtual;
import com.redeleitura.entity.LivrosLidos;
import com.redeleitura.entity.Usuario;
import com.redeleitura.util.GoogleBooksUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface LivroService {
    public LivrosLidos marcarLivroComoLido(Integer idUsuario, String isbn);

    public LivroAtual definirLivroAtual(Integer idUsuario, String isbn);
}
