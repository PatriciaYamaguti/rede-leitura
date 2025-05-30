package com.redeleitura.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "livro_atual")
@Data
public class LivroAtual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    @JsonIgnore
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
