package com.redeleitura.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "livro_atual")
@Data
public class LivroAtual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @Column(nullable = false)
    private String isbn;

    private String titulo;
    private String autor;

    public LivroAtual() {}

    public LivroAtual(Usuario usuario, String isbn, String titulo, String autor) {
        this.usuario = usuario;
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
    }
}
