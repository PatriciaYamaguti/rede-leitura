package com.redeleitura.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "livros_lidos", uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "isbn"}))
@Data
public class LivrosLidos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
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

