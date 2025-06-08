package com.redeleitura.dto;

import com.redeleitura.entity.Usuario;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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
