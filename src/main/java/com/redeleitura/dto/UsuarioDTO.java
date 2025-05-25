package com.redeleitura.dto;

import jakarta.persistence.Column;

import java.time.LocalDateTime;

public class UsuarioDTO {
    private String nome;
    private String usuario;
    private AcessoDTO acesso;
    private String livroAtualIsbn;
    private LocalDateTime dataCadastro;

    public UsuarioDTO() {}

    public UsuarioDTO(String nome, String usuario, AcessoDTO acesso, String livroAtualIsbn, LocalDateTime dataCadastro) {
        this.nome = nome;
        this.usuario = usuario;
        this.acesso = acesso;
        this.livroAtualIsbn = livroAtualIsbn;
        this.dataCadastro = dataCadastro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

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

    public String getLivroAtualIsbn() {
        return livroAtualIsbn;
    }

    public void setLivroAtualIsbn(String livroAtualIsbn) {
        this.livroAtualIsbn = livroAtualIsbn;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
