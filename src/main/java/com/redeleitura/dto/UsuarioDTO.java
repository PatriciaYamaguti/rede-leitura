package com.redeleitura.dto;

import com.redeleitura.entity.LivroAtual;

import java.time.LocalDateTime;

public class UsuarioDTO {

    private String nome;
    private String usuario;
    private AcessoDTO acesso;
    private LivroAtual livroAtual;
    private LocalDateTime dataCadastro;

    public UsuarioDTO() {}

    public UsuarioDTO(String nome, String usuario, AcessoDTO acesso, LocalDateTime dataCadastro) {
        this.nome = nome;
        this.usuario = usuario;
        this.acesso = acesso;
        this.dataCadastro = dataCadastro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LivroAtual getLivroAtual() {return livroAtual;}

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

    public void setLivroAtual(LivroAtual livroAtual) {
        this.livroAtual = livroAtual;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
