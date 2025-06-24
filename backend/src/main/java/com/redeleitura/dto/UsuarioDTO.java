package com.redeleitura.dto;

import java.time.LocalDateTime;

public class UsuarioDTO {

    private Integer id;
    private String nome;
    private String usuario;
    private String descricao;
    private AcessoDTO acesso;
    private LivroAtualDTO livroAtual;
    private LocalDateTime dataCadastro;
    public UsuarioDTO() {}

    public UsuarioDTO(String nome, String usuario, String descricao, AcessoDTO acesso, LocalDateTime dataCadastro, Integer id) {
        this.nome = nome;
        this.usuario = usuario;
        this.acesso = acesso;
        this.dataCadastro = dataCadastro;
        this.descricao = descricao;
        this.id = id;
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

    public AcessoDTO getAcesso() {
        return acesso;
    }

    public void setAcesso(AcessoDTO acesso) {
        this.acesso = acesso;
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
}
