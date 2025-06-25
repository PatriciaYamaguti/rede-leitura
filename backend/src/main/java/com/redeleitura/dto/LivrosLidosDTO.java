package com.redeleitura.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

public class LivrosLidosDTO {
    @Setter
    @Getter
    private UsuarioDTO usuarioDTO;
    @Setter
    @Getter
    private String isbn;
    @Setter
    @Getter
    private String titulo;
    @Setter
    @Getter
    private String autor;
    @Setter
    @Getter
    private LocalDateTime dataLeitura;

    public LivrosLidosDTO(UsuarioDTO usuarioDTO, String isbn, String titulo, String autor, LocalDateTime dataLeitura) {
        this.usuarioDTO = usuarioDTO;
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.dataLeitura = dataLeitura;
    }

    public LivrosLidosDTO() {}
}
