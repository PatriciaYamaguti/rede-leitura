package com.redeleitura.dto;

public class UsuarioLivrosEmComumDTO {
    private long quantidadeLivrosEmComum;
    private String nome;
    private String descricao;
    private String usuario; // suponho que aqui você quis dizer login ou nome de usuário

    public UsuarioLivrosEmComumDTO(long quantidadeLivrosEmComum, String nome, String descricao, String usuario) {
        this.quantidadeLivrosEmComum = quantidadeLivrosEmComum;
        this.nome = nome;
        this.descricao = descricao;
        this.usuario = usuario;
    }

    public UsuarioLivrosEmComumDTO() {}

    public long getQuantidadeLivrosEmComum() {
        return quantidadeLivrosEmComum;
    }

    public void setQuantidadeLivrosEmComum(long quantidadeLivrosEmComum) {
        this.quantidadeLivrosEmComum = quantidadeLivrosEmComum;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
