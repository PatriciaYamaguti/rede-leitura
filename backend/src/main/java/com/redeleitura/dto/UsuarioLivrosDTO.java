package com.redeleitura.dto;

import java.time.LocalDateTime;
import java.util.List;

public class UsuarioLivrosDTO {

    private Integer id;
    private String nome;
    private String usuario;
    private String descricao;
    private LivroAtualDTO livroAtual;
    private LocalDateTime dataCadastro;
    private List<LivrosLidosDTO> livrosLidos;

    public UsuarioLivrosDTO() {}

    public UsuarioLivrosDTO(String nome, String usuario, String descricao, LocalDateTime dataCadastro, Integer id, List<LivrosLidosDTO> livrosLidos) {
        this.nome = nome;
        this.usuario = usuario;
        this.dataCadastro = dataCadastro;
        this.descricao = descricao;
        this.id = id;
        this.livrosLidos = livrosLidos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LivroAtualDTO getLivroAtual() {return livroAtual;}

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setLivroAtual(LivroAtualDTO livroAtual) {
        this.livroAtual = livroAtual;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getDescricao() { return descricao; }

    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public List<LivrosLidosDTO> getLivrosLidos() { return livrosLidos; }

    public void setLivrosLidos(List<LivrosLidosDTO> livrosLidos) { this.livrosLidos = livrosLidos; }
}
