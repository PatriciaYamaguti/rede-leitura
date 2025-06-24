package com.redeleitura.dto;

import lombok.Getter;
import lombok.Setter;

public class LivroAtualDTO {
    @Getter
    @Setter
    private String isbn;
    @Getter
    @Setter
    private String titulo;
    @Getter
    @Setter
    private String autor;
    private UsuarioDTO usuario;

    public LivroAtualDTO() {}

    public LivroAtualDTO(UsuarioDTO usuario, String isbn, String titulo, String autor) {
        this.usuario = usuario;
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
    }

    public UsuarioDTO getUsuarioDTO() {
        return usuario;
    }

    public void setUsuarioDTO(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

}
