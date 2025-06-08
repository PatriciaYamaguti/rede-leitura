package com.redeleitura.dto;

public class UsuarioComInteresseDTO {
    private UsuarioDTO usuario;
    private long quantidadeLivrosEmComum;

    public UsuarioComInteresseDTO() {}

    public UsuarioComInteresseDTO(UsuarioDTO usuario, long quantidadeLivrosEmComum) {
        this.usuario = usuario;
        this.quantidadeLivrosEmComum = quantidadeLivrosEmComum;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public long getQuantidadeLivrosEmComum() {
        return quantidadeLivrosEmComum;
    }

    public void setQuantidadeLivrosEmComum(long quantidadeLivrosEmComum) {
        this.quantidadeLivrosEmComum = quantidadeLivrosEmComum;
    }
}
