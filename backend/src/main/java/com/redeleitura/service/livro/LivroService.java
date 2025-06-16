package com.redeleitura.service.livro;

import com.redeleitura.entity.LivroAtual;
import com.redeleitura.entity.LivrosLidos;


public interface LivroService {
    public LivrosLidos marcarLivroComoLido(Integer idUsuario, String isbn);

    public LivroAtual definirLivroAtual(Integer idUsuario, String isbn);
}
