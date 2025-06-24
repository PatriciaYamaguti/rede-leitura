package com.redeleitura.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Table(name = "livros_lidos", uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "isbn"}))
@Data
public class LivrosLidos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private String isbn;

    private String titulo;

    private String autor;

    @Column(name = "data_leitura")
    private LocalDateTime dataLeitura = LocalDateTime.now();
}

